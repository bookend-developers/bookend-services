package com.bookclupservice.bookclubservice.controller;

import com.bookclupservice.bookclubservice.expection.NotMemberExpection;
import com.bookclupservice.bookclubservice.model.*;
import com.bookclupservice.bookclubservice.payload.MessageResponse;
import com.bookclupservice.bookclubservice.payload.request.*;
import com.bookclupservice.bookclubservice.service.ClubService;
import com.bookclupservice.bookclubservice.service.MemberService;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.plaf.metal.MetalMenuBarUI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/club")
public class ClubController {

    @Autowired
    private ClubService clubService;
    @Autowired
    private MemberService memberService;
    @GetMapping("/")
    public List<Club> getClubs(){

        List<Club> publicClubs = clubService.getAll().stream()
                .filter(club -> club.isPrivate()!=true)
                .collect(Collectors.toList());
        return publicClubs;
    }

    @GetMapping("/{username}")
    public List<Club> getMyClubs(@PathVariable("username")String username){
        return clubService.getMyClubs(username);
    }

    @GetMapping("/{club-id}/posts")
    public List<Post> getClubPosts(@PathVariable("club-id") Long clubId){
        return clubService.getClubPosts(clubId);
    }
    @GetMapping("/{username}/invitations")
    public List<Invitation> getMemberInvitations(@PathVariable("username") String username){
        return clubService.getMemberInvitations(username);
    }

    @GetMapping("/member/{username}/posts")
    public List<Post> getWriterPosts(@PathVariable("username") String username){
        return clubService.getWriterPosts(username);
    }
    @GetMapping("/{username}/clubs")
    public List<Club> getUserClubs(@PathVariable("username") String username){

        return memberService.find(username).getClubs();
    }
    @GetMapping("/post/{postid}")
    public Post getPost(@PathVariable("postid") Long postId){
        return clubService.findPostByID(postId);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addClub(@RequestBody  NewClubRequest newClubRequest){

        Club club = clubService.saveClub(newClubRequest);
        return ResponseEntity.ok(club);
    }

    @PostMapping("/new-member")
    public ResponseEntity<?> addClubToMember(@RequestBody NewClubMemberRequest newClubMemberRequest,
                                             OAuth2Authentication auth){
        clubService.newMember(newClubMemberRequest,auth.getName());
        return ResponseEntity.ok(new MessageResponse("member added succesfully"));

    }


    @PostMapping("/invite-person")
    public ResponseEntity<?> invitePerson(@RequestBody InvitationRequest invitationRequest){

        Invitation invitation =clubService.invitePerson(invitationRequest);
        if(invitation==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You already invite this person.");
        }
        return ResponseEntity.ok(new MessageResponse("request sended successfully"));
    }
    @PostMapping("/reply-invitation")
    public ResponseEntity<?> acceptPerson(@RequestBody InvitationReply invitationReply){
        clubService.replyInvitation(invitationReply);
        return ResponseEntity.ok(new MessageResponse("request sended successfully"));
    }

    @PostMapping("/new-post")
    public ResponseEntity<?> sharePost(@RequestBody NewPostRequest newPostRequest,OAuth2Authentication auth){
        try {
            clubService.savePost(newPostRequest,auth);
        } catch (NotMemberExpection notMemberExpection) {
            return ResponseEntity.badRequest().body(new MessageResponse(notMemberExpection.getMessage()));
        }
        return ResponseEntity.ok(new MessageResponse("new post shared"));
    }
    @PostMapping("/{clubid}/post/comment")
    public ResponseEntity<?> commentPost(@RequestBody CommentRequest commentRequest,
                                         @PathVariable("clubid") Long clubId,
                                         OAuth2Authentication auth){
        Club club = clubService.findByID(clubId);

        commentRequest.setUsername(auth.getName());

        boolean check = club.getMembers()
                .stream()
                .anyMatch(m -> m.getUserName().equals(auth.getName()));
        if(check==false){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Only members can comment a post.");
        }
        clubService.sendComment(commentRequest);

        return ResponseEntity.ok(new MessageResponse("new comment shared"));
    }

}
