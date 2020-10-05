package com.bookclupservice.bookclubservice.payload.request;

public class NewPostAllowedMemberRequest {

    private Long clubId;
    private Long memberId;

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
