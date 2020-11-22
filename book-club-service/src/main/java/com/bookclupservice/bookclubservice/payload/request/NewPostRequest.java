package com.bookclupservice.bookclubservice.payload.request;

import com.bookclupservice.bookclubservice.model.Club;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class NewPostRequest {

    private String title;
    private String text;
    private Long clubId;
    private String username;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
