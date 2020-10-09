package com.bookclupservice.bookclubservice.model;

import javax.persistence.*;

@Entity
@Table(name = "club_member_requests")
public class ClubMemberRequest {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Club club;

    @ManyToOne
    private Member clubOwner;

    @ManyToOne
    private Member requestingMember;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Member getClubOwner() {
        return clubOwner;
    }

    public void setClubOwner(Member clubOwner) {
        this.clubOwner = clubOwner;
    }

    public Member getRequestingMember() {
        return requestingMember;
    }

    public void setRequestingMember(Member requestingMember) {
        this.requestingMember = requestingMember;
    }
}
