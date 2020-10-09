package com.bookclupservice.bookclubservice.model;

import javax.persistence.*;
import javax.websocket.OnMessage;

@Entity
@Table(name = "share_post_requests")
public class SharePostRequest {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
