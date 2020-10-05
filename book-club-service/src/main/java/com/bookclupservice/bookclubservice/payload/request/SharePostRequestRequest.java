package com.bookclupservice.bookclubservice.payload.request;


public class SharePostRequestRequest {

    private Long clubId;
    private String text;
    private Long requestingMember;

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getRequestingMember() {
        return requestingMember;
    }

    public void setRequestingMember(Long requestingMember) {
        this.requestingMember = requestingMember;
    }
}
