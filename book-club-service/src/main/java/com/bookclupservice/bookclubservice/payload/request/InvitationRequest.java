package com.bookclupservice.bookclubservice.payload.request;

public class InvitationRequest {

    private Long clubId;
    private String invitedPersonUserName;

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public String getInvitedPersonUserName() {
        return invitedPersonUserName;
    }

    public void setInvitedPersonUserName(String invitedPersonUserName) {
        this.invitedPersonUserName = invitedPersonUserName;
    }
}
