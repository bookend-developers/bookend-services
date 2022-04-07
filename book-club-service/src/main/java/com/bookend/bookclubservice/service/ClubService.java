package com.bookend.bookclubservice.service;

import com.bookend.bookclubservice.expection.ClubAlreadyExistException;
import com.bookend.bookclubservice.expection.NotMemberException;
import com.bookend.bookclubservice.kafka.MessageProducer;
import com.bookend.bookclubservice.payload.MailRequest;
import com.bookend.bookclubservice.model.Club;
import com.bookend.bookclubservice.model.Invitation;
import com.bookend.bookclubservice.model.Member;
import com.bookend.bookclubservice.model.Post;
import com.bookend.bookclubservice.payload.request.*;
import com.bookend.bookclubservice.repository.ClubRepository;
import com.bookend.bookclubservice.repository.InvitationRepository;
import com.bookend.bookclubservice.repository.MemberRepository;
import com.bookend.bookclubservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * ABCS-CSC stands for BookclubService-ClubServiceClass
 * SM stands for ServiceMethod
 */
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
    /**
     * ABCS-CSC-1 (SM_17)
     */
    public List<Club> getMyClubs(String username){
        Member member=memberRepository.findByUserName(username);
        if(member == null){
            throw new IllegalArgumentException("There is no such member with that username");
        }
        return clubRepository.findByOwner(member);

    }
    /**
     * ABCS-CSC-2 (SM_18)
     */
    public List<Invitation> getMemberInvitations(String username){
        Member member = memberRepository.findByUserName(username);
        if(member == null){
            throw new IllegalArgumentException("There is no such member with that username");
        }
        return invitationRepository.findInvitationsByInvitedPerson(member);
    }
    /**
     * ABCS-CSC-3 (SM_19)
     */
    public List<Post> getWriterPosts(String username){
        Member member = memberRepository.findByUserName(username);
        if(member == null){
            throw new IllegalArgumentException("There is no such member with that username");
        }
        return postRepository.findByWriter(member);
    }
    /**
     * ABCS-CSC-4 (SM_20)
     */
    public List<Post> getClubPosts(Long clubId){
        return postRepository.findByClubId(clubId);
    }

    /**
     * ABCS-CSC-5 (SM_21)
     */
    public Club saveClub(NewClubRequest newClubRequest){
        Member owner = memberRepository.findByUserName(newClubRequest.getUsername());
        if(owner == null){
            throw new IllegalArgumentException("There is no such member with that username");
        }
        if(newClubRequest.getClubName() == null || newClubRequest.getClubName() == ""){
            throw new IllegalArgumentException("There is no such member with that username");
        }
        if(clubRepository.findAll().stream().anyMatch(club -> club.getClubName().toLowerCase().matches(newClubRequest.getClubName().toLowerCase()))){
            throw new ClubAlreadyExistException("club already exist with that club name");
        }
        Club club = new Club();
        club.setClubName(newClubRequest.getClubName());
        club.setDescription(newClubRequest.getDescription());
        club.setPrivate(newClubRequest.isPrivatee());
        club.setOwner(owner);
        return clubRepository.save(club);
    }
    /**
     * ABCS-CSC-6 (SM_22)
     */
    public boolean newMember(NewClubMemberRequest newClubMemberRequest, String username){
        Member member = memberRepository.findByUserName(username);
        if(member!= null){
            return false;
        }

        Club club = clubRepository.findById(newClubMemberRequest.getClubId()).get();
        if(club.getOwner().getUserName().equals(username)){
            return false;
        }
        if(club.getMembers().stream().anyMatch(m-> m.getUserName().toLowerCase().matches(username.toLowerCase()))){
            return false;
        }
        member.getClubs().add(club);
        club.getMembers().add(member);
        memberRepository.save(member);
        clubRepository.save(club);
        return true;

    }
    /**
     * ABCS-CSC-7 (SM_23)
     */
    public Invitation invitePerson(InvitationRequest invitationRequest){
        Member invitedPerson = memberRepository.findByUserName(invitationRequest.getInvitedPersonUserName());
        if(invitedPerson==null){
            throw new IllegalArgumentException("member not exist with that username");
        }

        Club club = clubRepository.findById(invitationRequest.getClubId()).get();
        if(invitationRepository.findByClubAndInvitedPerson(club,invitedPerson)!=null){
            throw new IllegalArgumentException("this user already invited to this club");
        }
        if(invitedPerson.getUserName()==club.getOwner().getUserName()){
            throw new IllegalArgumentException("Illegal operation.");
        }
        Invitation invitation = new Invitation();
        invitation.setClub(club);
        invitation.setInvitedPerson(invitedPerson);
        Invitation newInvitation = invitationRepository.save(invitation);
        MailRequest mailRequest = new MailRequest(invitedPerson.getId(),"Invitation",club.getOwner().getUserName()+" invites you to "+club.getClubName() + " club");
        messageProducer.sendMailRequest(mailRequest);
        return newInvitation;
    }
    /**
     * ABCS-CSC-8 (SM_24)
     */
    public void replyInvitation(InvitationReply invitationReply){
        Invitation invitation = invitationRepository.findById(invitationReply.getInvitationId()).orElse(null);
        if(invitation==null){
            throw new IllegalArgumentException("Invitation does not exist with given id");
        }
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
    /**
     * ABCS-CSC-9 (SM_25)
     */
    public Post savePost(NewPostRequest newPostRequest, String principal) throws NotMemberException, IllegalArgumentException{
        Club club = clubRepository.findById(newPostRequest.getClubId()).orElse(null);
        Member writer = memberRepository.findByUserName(principal);
        if(club==null){
            throw new IllegalArgumentException("club does not exist with given id");
        }
        if(writer==null){
            throw new IllegalArgumentException("writer does not exist with given id");
        }
        if(newPostRequest.getText()==null || newPostRequest.getText()==""){
            throw new IllegalArgumentException("Text field is empty.");
        }
        if(newPostRequest.getTitle()==null || newPostRequest.getTitle()==""){
            throw new IllegalArgumentException("Title field is empty.");
        }
        if(club.getMembers().contains(writer) || club.getOwner().getUserName().equals(writer.getUserName())){
            Post post = new Post();
            post.setClub(club);
            post.setText(newPostRequest.getText());
            post.setTitle(newPostRequest.getTitle());
            post.setWriter(writer);

            return postRepository.save(post);
        }else
            throw new NotMemberException("user doesn't belong to club");
    }
    /**
     * ABCS-CSC-10 (SM_26)
     */
    public Post findPostByID(Long postID){
        Post post = postRepository.findPostById(postID);
        if(post==null)
            throw new IllegalArgumentException("post not exist with this id");
        return post;
    }
    /**
     * ABCS-CSC-11 (SM_27)
     */
    public Club findByID(Long clubId){
        Club club = clubRepository.findClubById(clubId);
        if(club==null)
            throw new IllegalArgumentException("club not exist with this id");
        return club;
    }
    /**
     * ABCS-CSC-12 (SM_28)
     */
    public void sendComment(CommentRequest commentRequest){
        messageProducer.sendCommentRequest(commentRequest);
    }
    /**
     * ABCS-CSC-13 (SM_29)
     */
    public List<Club> getMembershipClub(String name) {
        Member member = memberRepository.findByUserName(name);
        if(member==null)
            throw new IllegalArgumentException("member not exist with this id");
        return member.getClubs();
    }
}
