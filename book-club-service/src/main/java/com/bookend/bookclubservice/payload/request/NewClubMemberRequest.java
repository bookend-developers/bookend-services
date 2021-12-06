package com.bookend.bookclubservice.payload.request;

public class NewClubMemberRequest {

    private Long clubId;
    private Long memberId;

    public NewClubMemberRequest(Long clubId, Long memberId) {
        this.clubId = clubId;
        this.memberId = memberId;
    }
    public NewClubMemberRequest() {

    }
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
