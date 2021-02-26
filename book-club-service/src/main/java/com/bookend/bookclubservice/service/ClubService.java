package com.bookend.bookclubservice.service;

import com.bookend.bookclubservice.expection.ClubAllreadyExistExpection;
import com.bookend.bookclubservice.expection.NotMemberExpection;
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

    public List<Club> getMyClubs(String username){
        Member member=memberRepository.findByUserName(username);
        if(member == null){
            throw new IllegalArgumentException("There is no such member with that username");
        }
        return clubRepository.findByOwner(member);

    }
    public List<Invitation> getMemberInvitations(String username){
        Member member = memberRepository.findByUserName(username);
        if(member == null){
            throw new IllegalArgumentException("There is no such member with that username");
        }
        return invitationRepository.findInvitationsByInvitedPerson(member);
    }

    public List<Post> getWriterPosts(String username){
        Member member = memberRepository.findByUserName(username);
        if(member == null){
            throw new IllegalArgumentException("There is no such member with that username");
        }
        return postRepository.findByWriter(member);
    }

    public List<Post> getClubPosts(Long clubId){
        return postRepository.findByClubId(clubId);
    }


    public Club saveClub(NewClubRequest newClubRequest){
        Member owner = memberRepository.findByUserName(newClubRequest.getUsername());
        if(owner == null){
            throw new IllegalArgumentException("There is no such member with that username");
        }
        if(clubRepository.findAll().stream().anyMatch(club -> club.getClubName().toLowerCase().matches(newClubRequest.getClubName().toLowerCase()))){
            throw new ClubAllreadyExistExpection("club allready exist with that clubname");
        }
        Club club = new Club();
        club.setClubName(newClubRequest.getClubName());
        club.setDescription(newClubRequest.getDescription());
        club.setPrivate(newClubRequest.isPrivatee());
        club.setOwner(owner);
        return clubRepository.save(club);
    }

    public boolean newMember(NewClubMemberRequest newClubMemberRequest, String username){
        Member member = memberRepository.findByUserName(username);
        if(member== null){
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

    public Invitation invitePerson(InvitationRequest invitationRequest){
        Member invitedPerson = memberRepository.findByUserName(invitationRequest.getInvitedPersonUserName());
        if(invitedPerson==null){
            throw new IllegalArgumentException("member not exist with that username");
        }
        Club club = clubRepository.findById(invitationRequest.getClubId()).get();
        if(invitationRepository.findByClubAndInvitedPerson(club,invitedPerson)!=null){
            throw new IllegalArgumentException("this user allready invited to this club");
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

    public Post savePost(NewPostRequest newPostRequest, String principal) throws NotMemberExpection {
        Club club = clubRepository.findById(newPostRequest.getClubId()).orElse(null);
        Member writer = memberRepository.findByUserName(principal);
        if(club==null){
            throw new IllegalArgumentException("club does not exist with given id");
        }
        if(writer==null){
            throw new IllegalArgumentException("writer does not exist with given id");
        }
        if(club.getMembers().contains(writer) || club.getOwner().getUserName().equals(writer.getUserName())){
            Post post = new Post();
            post.setClub(club);
            post.setText(newPostRequest.getText());
            post.setTitle(newPostRequest.getTitle());
            post.setWriter(writer);

            return postRepository.save(post);
        }else
            throw new NotMemberExpection("user doesn't belong to club");
    }
    public Post findPostByID(Long postID){
        Post post = postRepository.findPostById(postID);
        if(post==null)
            throw new IllegalArgumentException("post not exist with this id");
        return post;
    }
    public Club findByID(Long clubId){
        Club club = clubRepository.findClubById(clubId);
        if(club==null)
            throw new IllegalArgumentException("club not exist with this id");
        return club;
    }
    public void sendComment(CommentRequest commentRequest){
        messageProducer.sendCommentRequest(commentRequest);
    }

    public List<Club> getMembershipClub(String name) {
        Member member = memberRepository.findByUserName(name);
        if(member==null)
            throw new IllegalArgumentException("member not exist with this id");
        return member.getClubs();
    }
}