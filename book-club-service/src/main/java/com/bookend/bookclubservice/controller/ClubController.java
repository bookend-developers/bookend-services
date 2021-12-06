package com.bookend.bookclubservice.controller;

import com.bookend.bookclubservice.expection.NotMemberException;

import com.bookend.bookclubservice.model.Club;
import com.bookend.bookclubservice.model.Invitation;
import com.bookend.bookclubservice.model.Member;
import com.bookend.bookclubservice.model.Post;
import com.bookend.bookclubservice.payload.MessageResponse;

import com.bookend.bookclubservice.service.ClubService;
import com.bookend.bookclubservice.service.MemberService;
import com.bookend.bookclubservice.payload.request.*;
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

/**
 * BCS-CC stands for BookclubService-ClubController
 * CM stands for ControllerMethod
 */
@RestController
@RequestMapping("/api/club")
public class ClubController {

    @Autowired
    private ClubService clubService;
    @Autowired
    private MemberService memberService;

    /**
     * BCS-CC-1 (CM_12)
     */
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
    /**
     * BCS-CC-2 (CM_13)
     */
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
    /**
     * BCS-CC-3 (CM_14)
     */
    @ApiOperation(value = "Get Clubs of the user", response = Club.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved club list"),
            @ApiResponse(code = 401, message = "You are not authorized to view resource.")
    })

    @GetMapping("/{username}")
    public List<Club> getMyClubs(@PathVariable("username")String username){
        try{
            return clubService.getMyClubs(username);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,illegalArgumentException.getMessage());
        }


    }
    /**
     * BCS-CC-4 (CM_15)
     */
    @ApiOperation(value = "Get Club's Post", response = Post.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved post list"),
            @ApiResponse(code = 401, message = "You are not authorized to view resource.")
    })
    @GetMapping("/{club-id}/posts")
    public List<Post> getClubPosts(@PathVariable("club-id") Long clubId){
        return clubService.getClubPosts(clubId);
    }
    /**
     * BCS-CC-5 (CM_16)
     */
    @ApiOperation(value = "Get list of invitations of clubs for the user", response = Invitation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved invitation list"),
            @ApiResponse(code = 401, message = "You are not authorized to view resource.")
    })
    @GetMapping("/{username}/invitations")
    public List<Invitation> getMemberInvitations(@PathVariable("username") String username){
        return clubService.getMemberInvitations(username);
    }
   /* @ApiOperation(value = "Get posts for specific user", response = Post.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved posts"),
            @ApiResponse(code = 401, message = "You are not authorized to view resource.")
    })
    @GetMapping("/member/{username}/posts")
    public List<Post> getWriterPosts(@PathVariable("username") String username){
        return clubService.getWriterPosts(username);
    }*/
    /**
     * BCS-CC-6 (CM_17)
     */
    @ApiOperation(value = "Get  specific post", response = Post.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved post"),
            @ApiResponse(code = 401, message = "You are not authorized to view resource.")
    })
    @GetMapping("/post/{postid}")
    public Post getPost(@PathVariable("postid") Long postId){
        return clubService.findPostByID(postId);
    }

    /**
     * BCS-CC-7 (CM_18)
     */
    @ApiOperation(value = "Add new club", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Club added successfully "),
            @ApiResponse(code = 401, message = "You are not authorized to add resource.")
    })
    @PostMapping("/add")
    public ResponseEntity<?> addClub(@RequestBody NewClubRequest newClubRequest){
        try{
            Club club = clubService.saveClub(newClubRequest);
            if(club==null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Club name is already in use");
            }
            return ResponseEntity.ok(club);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,illegalArgumentException.getMessage());
        }


    }
    /**
     * BCS-CC-8 (CM_19)
     */
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
    /**
     * BCS-CC-9 (CM_20)
     */
    @ApiOperation(value = "Invite a user to the private club", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved posts"),
            @ApiResponse(code = 401, message = "You are not authorized to view resource.")
    })
    @PostMapping("/invite-person")
    public ResponseEntity<?> invitePerson(@RequestBody InvitationRequest invitationRequest){
        try{
            Invitation invitation =clubService.invitePerson(invitationRequest);
            if(invitation==null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You already invite this person or person does not exist.");
            }
        }
         catch (IllegalArgumentException illegalArgumentException) {
            return ResponseEntity.badRequest().body(new MessageResponse(illegalArgumentException.getMessage()));
        }
        return ResponseEntity.ok(new MessageResponse("request sent successfully"));
    }
    /**
     * BCS-CC-10 (CM_21)
     */
    @ApiOperation(value = "Reply invitation as reject or accept", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully replied invitation"),
            @ApiResponse(code = 401, message = "You are not authorized to reply resource.")
    })
    @PostMapping("/reply-invitation")
    public ResponseEntity<?> acceptPerson(@RequestBody InvitationReply invitationReply){
        clubService.replyInvitation(invitationReply);
        return ResponseEntity.ok(new MessageResponse("successfully"));
    }
    /**
     * BCS-CC-11 (CM_22)
     */
    @ApiOperation(value = "Share new post", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully shared post."),
            @ApiResponse(code = 401, message = "You are not authorized to share post."),
            @ApiResponse(code = 400, message = "You are trying to post with invalid data.")
    })
    @PostMapping("/new-post")
    public ResponseEntity<?> sharePost(@RequestBody NewPostRequest newPostRequest, OAuth2Authentication auth){
        try {
            clubService.savePost(newPostRequest,(String) auth.getPrincipal());
        } catch (NotMemberException notMemberException) {
            return ResponseEntity.badRequest().body(new MessageResponse(notMemberException.getMessage()));
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,illegalArgumentException.getMessage());
        }
        return ResponseEntity.ok(new MessageResponse("new post shared"));
    }
    /**
     * BCS-CC-12 (CM_23)
     */
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
        if(commentRequest.getComment()==null||commentRequest.getComment()==""){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Mandatory field is empty.");
        }
        Club club = post.getClub();
        commentRequest.setUsername(auth.getName());
        boolean isOwner = club.getOwner().getUserName().equalsIgnoreCase(auth.getName());
        boolean isMember = club.getMembers()
                .stream()
                .anyMatch(m -> m.getUserName().equalsIgnoreCase(auth.getName()));
        if(!isMember && !isOwner){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Only members can comment a post.");
        }
        clubService.sendComment(commentRequest);

        return ResponseEntity.ok(new MessageResponse("new comment shared"));
    }
    /**
     * BCS-CC-13 (CM_24)
     */
    @GetMapping("/clubs")
    public List<Club> getMyMembershipClub(OAuth2Authentication auth){
        return clubService.getMembershipClub( auth.getName());
    }

}
