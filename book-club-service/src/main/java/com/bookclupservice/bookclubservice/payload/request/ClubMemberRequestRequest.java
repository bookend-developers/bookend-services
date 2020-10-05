package com.bookclupservice.bookclubservice.payload.request;

import com.bookclupservice.bookclubservice.model.Club;
import com.bookclupservice.bookclubservice.model.Member;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ClubMemberRequestRequest {

    private Long clubId;

    private Long requestingMemberId;

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public Long getRequestingMemberId() {
        return requestingMemberId;
    }

    public void setRequestingMemberId(Long requestingMemberId) {
        this.requestingMemberId = requestingMemberId;
    }
}
