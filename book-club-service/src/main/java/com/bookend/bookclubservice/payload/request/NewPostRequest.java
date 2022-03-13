package com.bookend.bookclubservice.payload.request;

public class NewPostRequest {

    private String title;
    private String text;
    private Long clubId;

    public NewPostRequest(String title, String text, Long clubId) {
        this.title = title;
        this.text = text;
        this.clubId = clubId;
    }

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
}