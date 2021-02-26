package com.bookend.bookclubservice.model;

import javax.persistence.*;

@Entity
@Table(name = "invitations")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Club club;

    @ManyToOne
    private Member invitedPerson;

    public Invitation() {
    }
    public Invitation(Long id, Club club, Member invitedPerson) {
        this.id=id;
        this.invitedPerson=invitedPerson;
        this.club=club;
    }

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

    public Member getInvitedPerson() {
        return invitedPerson;
    }

    public void setInvitedPerson(Member invitedPerson) {
        this.invitedPerson = invitedPerson;
    }
}
