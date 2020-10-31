package com.bookclupservice.bookclubservice.controller;

import com.bookclupservice.bookclubservice.model.Club;
import com.bookclupservice.bookclubservice.model.Post;
import com.bookclupservice.bookclubservice.model.SharePostRequest;
import com.bookclupservice.bookclubservice.payload.MessageResponse;
import com.bookclupservice.bookclubservice.payload.request.*;
import com.bookclupservice.bookclubservice.service.ClubService;
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

    @GetMapping("/")
    public List<Club> getClubs(){
        return clubService.getAll();
    }

    @GetMapping("/requests/{club-id}")
    public List<SharePostRequest> getRequests(@PathVariable("club-id") Long clubId){
        return clubService.getPostRequests(clubId);
    }
    @GetMapping("/post/{club-id}")
    public List<Post> getClubPosts(@PathVariable("club-id") Long clubId){
        return clubService.getClubPosts(clubId);
    }
    @GetMapping("/post/{writer-id}")
    public List<Post> getWriterPosts(@PathVariable("writer-id") Long writerId){
        return clubService.getClubPosts(writerId);
    }

    @PostMapping("/")
    public ResponseEntity<?> addClub(@RequestBody  NewClubRequest newClubRequest){

        clubService.saveClub(newClubRequest);
        return ResponseEntity.ok(new MessageResponse("club added succesfully"));
    }

    @PostMapping("/new-member")
    public ResponseEntity<?> addClubToMember(@RequestBody NewClubMemberRequest newClubMemberRequest){
        clubService.newMember(newClubMemberRequest);
        return ResponseEntity.ok(new MessageResponse("member added succesfully"));

    }
    @PostMapping("/request-membership")
    public ResponseEntity<?> requestMembership(@RequestBody ClubMemberRequestRequest clubMemberRequestRequest){
        clubService.requestMembership(clubMemberRequestRequest);
        return ResponseEntity.ok(new MessageResponse("request sended successfully"));
    }

    @PostMapping("/request-post-permission")
    public ResponseEntity<?> requestPermission(@RequestBody SharePostRequestRequest sharePostRequestRequest){
        clubService.requestPermission(sharePostRequestRequest);
        return ResponseEntity.ok(new MessageResponse("Permission request sended."));

    }

    @PostMapping("/allow-member")
    public ResponseEntity<?> givePermission(@RequestBody NewPostAllowedMemberRequest newPostAllowedMemberRequest){
        clubService.allowMemberForPost(newPostAllowedMemberRequest);
        return ResponseEntity.ok(new MessageResponse("member now has post permission"));

    }

    @PostMapping("/new-post")
    public ResponseEntity<?> sharePost(@RequestBody NewPostRequest newPostRequest){
        clubService.savePost(newPostRequest);
        return ResponseEntity.ok(new MessageResponse("new post shared"));
    }
}
