package com.bookclupservice.bookclubservice.controller;

import com.bookclupservice.bookclubservice.model.Club;
import com.bookclupservice.bookclubservice.model.Post;
import com.bookclupservice.bookclubservice.model.SharePostRequest;
import com.bookclupservice.bookclubservice.payload.MessageResponse;
import com.bookclupservice.bookclubservice.payload.request.*;
import com.bookclupservice.bookclubservice.service.ClubService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/club")
public class ClubController {

    @Autowired
    private ClubService clubService;

    @ApiOperation(value = "Get all clubs", response = Club.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved club list"),
            @ApiResponse(code = 401, message = "You are not authorized to view resource.")
    })
    @GetMapping("/")
    public List<Club> getClubs(){
        return clubService.getAll();
    }

    @ApiOperation(value = "Get request for post", response = SharePostRequest.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully requested  post"),
            @ApiResponse(code = 401, message = "You are not authorized to request for resource.")
    })
    @GetMapping("/requests/{club-id}")
    public List<SharePostRequest> getRequests(@PathVariable("club-id") Long clubId){
        return clubService.getRequests(clubId);
    }

    @ApiOperation(value = "Get Club's Post", response = Post.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved post list"),
            @ApiResponse(code = 401, message = "You are not authorized to view resource.")
    })
    @GetMapping("/post/{club-id}")
    public List<Post> getClubPosts(@PathVariable("club-id") Long clubId){
        return clubService.getClubPosts(clubId);
    }

    @ApiOperation(value = "Get posts for specific user", response = Post.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved posts"),
            @ApiResponse(code = 401, message = "You are not authorized to view resource.")
    })
    @GetMapping("/post/{writer-id}")
    public List<Post> getWriterPosts(@PathVariable("writer-id") Long writerId){
        return clubService.getClubPosts(writerId);
    }
    @ApiOperation(value = "Add new club", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Club added successfully "),
            @ApiResponse(code = 401, message = "You are not authorized to add resource.")
    })
    @PostMapping("/")
    public ResponseEntity<?> addClub(@RequestBody  NewClubRequest newClubRequest){

        clubService.saveClub(newClubRequest);
        return ResponseEntity.ok(new MessageResponse("Club added successfully"));
    }
    @ApiOperation(value = "Add new member to club", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added member"),
            @ApiResponse(code = 401, message = "You are not authorized to add member.")
    })
    @PostMapping("/new-member")
    public ResponseEntity<?> addClubToMember(@RequestBody NewClubMemberRequest newClubMemberRequest){
        clubService.newMember(newClubMemberRequest);
        return ResponseEntity.ok(new MessageResponse("member added successfully"));

    }
    @ApiOperation(value = "Request for membership", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully requested membership"),
            @ApiResponse(code = 401, message = "You are not authorized to request membership.")
    })
    @PostMapping("/request-membership")
    public ResponseEntity<?> requestMembership(@RequestBody ClubMemberRequestRequest clubMemberRequestRequest){
        clubService.requestMembership(clubMemberRequestRequest);
        return ResponseEntity.ok(new MessageResponse("request sended successfully"));
    }
    @ApiOperation(value = "Request permission", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Permission request sent successfully."),
            @ApiResponse(code = 401, message = "You are not authorized to request permission.")
    })
    @PostMapping("/request-post-permission")
    public ResponseEntity<?> requestPermission(@RequestBody SharePostRequestRequest sharePostRequestRequest){
        clubService.requestPermission(sharePostRequestRequest);
        return ResponseEntity.ok(new MessageResponse("Permission request sent."));

    }
    @ApiOperation(value = "Give permission for post", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully given permission"),
            @ApiResponse(code = 401, message = "You are not authorized to give permission.")
    })
    @PostMapping("/allow-member")
    public ResponseEntity<?> givePermission(@RequestBody NewPostAllowedMemberRequest newPostAllowedMemberRequest){
        clubService.allowMemberForPost(newPostAllowedMemberRequest);
        return ResponseEntity.ok(new MessageResponse("member now has post permission"));

    }
    @ApiOperation(value = "Share new post", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully shared post."),
            @ApiResponse(code = 401, message = "You are not authorized to share post.")
    })
    @PostMapping("/new-post")
    public ResponseEntity<?> sharePost(@RequestBody NewPostRequest newPostRequest){
        clubService.savePost(newPostRequest);
        return ResponseEntity.ok(new MessageResponse("new post shared"));
    }
}
