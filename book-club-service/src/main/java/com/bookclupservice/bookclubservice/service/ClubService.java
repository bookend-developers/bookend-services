package com.bookclupservice.bookclubservice.service;

import com.bookclupservice.bookclubservice.kafka.MessageProducer;
import com.bookclupservice.bookclubservice.model.*;
import com.bookclupservice.bookclubservice.payload.MailRequest;
import com.bookclupservice.bookclubservice.payload.request.*;
import com.bookclupservice.bookclubservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubService {

    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SharePostRequestRepository sharePostRequestRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ClubMemberRequestRepository clubMemberRequestRepository;
    @Autowired
    private MessageProducer messageProducer;

    public List<Club> getAll(){
        return clubRepository.findAll();
    }

    public List<SharePostRequest> getRequests(Long clubId){
        List<SharePostRequest> requests = sharePostRequestRepository.findByClubId(clubId);
        return requests;
    }

    public List<Post> getWriterPosts(Long writerId){
        return postRepository.findByWriterId(writerId);
    }
    public List<Post> getClubPosts(Long clubId){
        return postRepository.findByWriterId(clubId);
    }

    public Club saveClub(NewClubRequest newClubRequest){
        Member owner = memberRepository.findById(newClubRequest.getMemberId()).get();
        Club club = new Club();
        club.setClubName(newClubRequest.getClubName());
        club.setDescription(newClubRequest.getDescription());
        club.setPrivate(newClubRequest.isPrivatee());
        club.setOwner(owner);
        return clubRepository.save(club);
    }

    public void newMember(NewClubMemberRequest newClubMemberRequest){
        Member member = memberRepository.findById(newClubMemberRequest.getMemberId()).get();
        Club club = clubRepository.findById(newClubMemberRequest.getClubId()).get();
        member.getClubs().add(club);
        club.getMembers().add(member);
        clubRepository.save(club);
        memberRepository.save(member);
    }

    public void requestMembership(ClubMemberRequestRequest clubMemberRequestRequest){
        Member member = memberRepository.findById(clubMemberRequestRequest.getRequestingMemberId()).get();
        Club club = clubRepository.findById(clubMemberRequestRequest.getClubId()).get();
        ClubMemberRequest clubMemberRequest = new ClubMemberRequest();
        clubMemberRequest.setClub(club);
        clubMemberRequest.setClubOwner(club.getOwner());
        clubMemberRequest.setRequestingMember(member);
        clubMemberRequestRepository.save(clubMemberRequest);

        MailRequest mailRequest = new MailRequest(club.getOwner().getId(),"Membership Request",member.getUserName()+" wants to join your "+club.getClubName() + " club");
        messageProducer.sendMailRequest(mailRequest);
    }

    public void requestPermission(SharePostRequestRequest sharePostRequestRequest){
        Club club = clubRepository.findById(sharePostRequestRequest.getClubId()).get();
        Member requestingMember = memberRepository.findById(sharePostRequestRequest.getRequestingMember()).get();
        SharePostRequest sharePostRequest = new SharePostRequest();
        sharePostRequest.setClub(club);
        sharePostRequest.setClubOwner(club.getOwner());
        sharePostRequest.setRequestingMember(requestingMember);
        sharePostRequest.setText(sharePostRequestRequest.getText());
        sharePostRequestRepository.save(sharePostRequest);

        MailRequest mailRequest = new MailRequest(club.getOwner().getId(),"Share Post Request",requestingMember.getUserName()+" wants to post writings in your "+club.getClubName() + " club");
        messageProducer.sendMailRequest(mailRequest);
    }


    public void allowMemberForPost(NewPostAllowedMemberRequest newPostAllowedMemberRequest){
        Member member = memberRepository.findById(newPostAllowedMemberRequest.getMemberId()).get();
        Club club = clubRepository.findById(newPostAllowedMemberRequest.getClubId()).get();
        member.getAllowedClubs().add(club);
        club.getPostMembers().add(member);
        clubRepository.save(club);
        memberRepository.save(member);

        MailRequest mailRequest = new MailRequest(member.getId(),"Post Permission","From now on you can share posts in "+club.getClubName() + " club");
        messageProducer.sendMailRequest(mailRequest);
    }

    public void savePost(NewPostRequest newPostRequest){
        Club club = clubRepository.findById(newPostRequest.getClubId()).get();
        Member writer = memberRepository.findById(newPostRequest.getWriterId()).get();
        Post post = new Post();
        post.setClub(club);
        post.setText(newPostRequest.getText());
        post.setTitle(newPostRequest.getTitle());
        post.setWriter(writer);
        postRepository.save(post);
    }

}
