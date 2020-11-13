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
    private PostRepository postRepository;
    @Autowired
    private MessageProducer messageProducer;
    @Autowired
    private InvitationRepository invitationRepository;

    public List<Club> getAll(){
        return clubRepository.findAll();
    }

    public List<Club> getMyClubs(Long ownerId){
        return clubRepository.findByOwnerId(ownerId);
    }
    public List<Invitation> getMemberInvitations(String username){
        Member member = memberRepository.findByUserName(username);
        return invitationRepository.findInvitationsByInvitedPerson(member);
    }

    public List<Post> getWriterPosts(Long writerId){
        return postRepository.findByWriterId(writerId);
    }
    public List<Post> getClubPosts(Long clubId){
        return postRepository.findByClubId(clubId);
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

    public Invitation invitePerson(InvitationRequest invitationRequest){
        Member invitedPerson = memberRepository.findByUserName(invitationRequest.getInvitedPersonUserName());
        Club club = clubRepository.findById(invitationRequest.getClubId()).get();
        if(invitationRepository.findByClubAndInvitedPerson(club,invitedPerson)!=null){
            return null;
        }
        Invitation invitation = new Invitation();
        invitation.setClub(club);
        invitation.setInvitedPerson(invitedPerson);
        Invitation newInvitation = invitationRepository.save(invitation);
        MailRequest mailRequest = new MailRequest(invitedPerson.getId(),"Invitation",club.getOwner().getUserName()+" invites you to "+club.getClubName() + " club");
        messageProducer.sendMailRequest(mailRequest);
        return newInvitation;
    }
    public void replyInvitation(InvitationReply invitationReply){
        Invitation invitation = invitationRepository.findById(invitationReply.getInvitationId()).get();
        Club club = invitation.getClub();
        MailRequest mailRequest;
        if(invitationReply.geteInvitationReply().equals(EInvitationReply.ACCEPT)){
            mailRequest = new MailRequest(club.getOwner().getId(),"Invitation Accepted",invitation.getInvitedPerson().getUserName() +"accepted your invite.");
            Member member = invitation.getInvitedPerson();
            member.getClubs().add(club);
            club.getMembers().add(invitation.getInvitedPerson());
            clubRepository.save(club);
            memberRepository.save(member);
        }
        else{
            mailRequest = new MailRequest(club.getOwner().getId(),"Invitation Rejected",invitation.getInvitedPerson().getUserName() +"rejected your invite.");
        }
        messageProducer.sendMailRequest(mailRequest);
        invitationRepository.delete(invitation);
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
