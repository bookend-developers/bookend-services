package com.bookclupservice.bookclubservice.controller;

import com.bookclupservice.bookclubservice.model.*;
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

    @GetMapping("/{owner-id}")
    public List<Club> getMyClubs(@PathVariable("owner-id")Long ownerId){
        return clubService.getMyClubs(ownerId);
    }

    @GetMapping("{club-id}/posts")
    public List<Post> getClubPosts(@PathVariable("club-id") Long clubId){
        return clubService.getClubPosts(clubId);
    }
    @GetMapping("{user-id}/invitations")
    public List<Invitation> getMemberInvitations(@PathVariable("user-id") Long userId){
        return clubService.getMemberInvitations(userId);
    }

    @GetMapping("/{writer-id}/posts")
    public List<Post> getWriterPosts(@PathVariable("writer-id") Long writerId){
        return clubService.getClubPosts(writerId);
    }

    @PostMapping("/")
    public ResponseEntity<?> addClub(@RequestBody  NewClubRequest newClubRequest){

        Club club = clubService.saveClub(newClubRequest);
        return ResponseEntity.ok(club);
    }

    @PostMapping("/new-member")
    public ResponseEntity<?> addClubToMember(@RequestBody NewClubMemberRequest newClubMemberRequest){
        clubService.newMember(newClubMemberRequest);
        return ResponseEntity.ok(new MessageResponse("member added succesfully"));

    }

    @PostMapping("/invite-person")
    public ResponseEntity<?> invitePerson(@RequestBody InvitationRequest invitationRequest){
        clubService.invitePerson(invitationRequest);
        return ResponseEntity.ok(new MessageResponse("request sended successfully"));
    }
    @PostMapping("/reply-invitation")
    public ResponseEntity<?> invitePerson(@RequestBody InvitationReply invitationReply){
        clubService.replyInvitation(invitationReply);
        return ResponseEntity.ok(new MessageResponse("request sended successfully"));
    }

    @PostMapping("/new-post")
    public ResponseEntity<?> sharePost(@RequestBody NewPostRequest newPostRequest){
        clubService.savePost(newPostRequest);
        return ResponseEntity.ok(new MessageResponse("new post shared"));
    }

}
