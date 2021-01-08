package com.bookclupservice.bookclubservice.controller;

import com.bookclupservice.bookclubservice.expection.NotMemberExpection;
import com.bookclupservice.bookclubservice.model.*;
import com.bookclupservice.bookclubservice.payload.MessageResponse;
import com.bookclupservice.bookclubservice.payload.request.*;
import com.bookclupservice.bookclubservice.service.ClubService;
import com.bookclupservice.bookclubservice.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/club")
public class ClubController {

    @Autowired
    private ClubService clubService;
    @Autowired
    private MemberService memberService;
    @ApiOperation(value = "Get all clubs", response = Club.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved club list"),
            @ApiResponse(code = 401, message = "You are not authorized to view resource.")
    })
    @GetMapping("/")
    public List<Club> getClubs(){

        List<Club> publicClubs = clubService.getAll().stream()
                .filter(club -> club.isPrivate()!=true)
                .collect(Collectors.toList());
        return publicClubs;
    }
    @ApiOperation(value = "Get all members of club", response = Member.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved member list"),
            @ApiResponse(code = 401, message = "You are not authorized to view resource."),
            @ApiResponse(code = 404, message = "Club is not found.")
    })
    @GetMapping("/{clubid}/members")
    public List<Member> getClubMembers(@PathVariable("clubid") Long clubId){
        Club club = clubService.findByID(clubId);

        return club.getMembers();
    }
    @ApiOperation(value = "Get Clubs of the user", response = Club.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved club list"),
            @ApiResponse(code = 401, message = "You are not authorized to view resource.")
    })

    @GetMapping("/{username}")
    public List<Club> getMyClubs(@PathVariable("username")String username){
        return clubService.getMyClubs(username);

    }
    @ApiOperation(value = "Get Club's Post", response = Post.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved post list"),
            @ApiResponse(code = 401, message = "You are not authorized to view resource.")
    })
    @GetMapping("/{club-id}/posts")
    public List<Post> getClubPosts(@PathVariable("club-id") Long clubId){
        return clubService.getClubPosts(clubId);
    }
    @ApiOperation(value = "Get list of invitations of clubs for the user", response = Invitation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved invitation list"),
            @ApiResponse(code = 401, message = "You are not authorized to view resource.")
    })
    @GetMapping("/{username}/invitations")
    public List<Invitation> getMemberInvitations(@PathVariable("username") String username){
        return clubService.getMemberInvitations(username);
    }
    @ApiOperation(value = "Get posts for specific user", response = Post.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved posts"),
            @ApiResponse(code = 401, message = "You are not authorized to view resource.")
    })
    @GetMapping("/member/{username}/posts")
    public List<Post> getWriterPosts(@PathVariable("username") String username){
        return clubService.getWriterPosts(username);
    }
    @ApiOperation(value = "Get  specific post", response = Post.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved post"),
            @ApiResponse(code = 401, message = "You are not authorized to view resource.")
    })
    @GetMapping("/post/{postid}")
    public Post getPost(@PathVariable("postid") Long postId){
        return clubService.findPostByID(postId);
    }


    @ApiOperation(value = "Add new club", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Club added successfully "),
            @ApiResponse(code = 401, message = "You are not authorized to add resource.")
    })
    @PostMapping("/add")
    public ResponseEntity<?> addClub(@RequestBody  NewClubRequest newClubRequest){

        Club club = clubService.saveClub(newClubRequest);
        if(club==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Club name is already in use");
        }
        return ResponseEntity.ok(club);
    }
    @ApiOperation(value = "Add new member to club", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added member"),
            @ApiResponse(code = 401, message = "You are not authorized to add member.")
    })
    @PostMapping("/new-member")
    public ResponseEntity<?> addClubToMember(@RequestBody NewClubMemberRequest newClubMemberRequest,
                                             OAuth2Authentication auth){
        boolean check = clubService.newMember(newClubMemberRequest,auth.getName());
        if(!check){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You are already a member.");
        }
        return ResponseEntity.ok(new MessageResponse("member added successfully"));

    }

    @ApiOperation(value = "Invite a user to the private club", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved posts"),
            @ApiResponse(code = 401, message = "You are not authorized to view resource.")
    })
    @PostMapping("/invite-person")
    public ResponseEntity<?> invitePerson(@RequestBody InvitationRequest invitationRequest){

        Invitation invitation =clubService.invitePerson(invitationRequest);
        if(invitation==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You already invite this person or person does not exist.");
        }
        return ResponseEntity.ok(new MessageResponse("request sent successfully"));
    }
    @ApiOperation(value = "Reply invitation as reject or accept", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully replied invitation"),
            @ApiResponse(code = 401, message = "You are not authorized to reply resource.")
    })
    @PostMapping("/reply-invitation")
    public ResponseEntity<?> acceptPerson(@RequestBody InvitationReply invitationReply){
        clubService.replyInvitation(invitationReply);
        return ResponseEntity.ok(new MessageResponse("request sent successfully"));
    }
    @ApiOperation(value = "Share new post", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully shared post."),
            @ApiResponse(code = 401, message = "You are not authorized to share post.")
    })
    @PostMapping("/new-post")
    public ResponseEntity<?> sharePost(@RequestBody NewPostRequest newPostRequest,OAuth2Authentication auth){
        try {
            clubService.savePost(newPostRequest,auth);
        } catch (NotMemberExpection notMemberExpection) {
            return ResponseEntity.badRequest().body(new MessageResponse(notMemberExpection.getMessage()));
        }
        return ResponseEntity.ok(new MessageResponse("new post shared"));
    }
    @ApiOperation(value = "Comment a post", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully commented."),
            @ApiResponse(code = 401, message = "You are not authorized to comment resource."),
            @ApiResponse(code = 403, message = "Only members can comment a post."),
            @ApiResponse(code = 404, message = "Post is not found")
    })
    @PostMapping("/post/comment")
    public ResponseEntity<?> commentPost(@RequestBody CommentRequest commentRequest,
                                         OAuth2Authentication auth){

        Post post = clubService.findPostByID(commentRequest.getPostID());
        if(post==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Post is not found");
        }
        Club club = post.getClub();
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

    @GetMapping("/clubs")
    public List<Club> getMyMembershipClub(OAuth2Authentication auth){
        return clubService.getMembershipClub( auth.getName());
    }

}
