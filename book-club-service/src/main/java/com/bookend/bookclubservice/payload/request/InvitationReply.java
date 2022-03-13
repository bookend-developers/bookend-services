package com.bookend.bookclubservice.payload.request;

public class InvitationReply {

    private Long invitationId;
    private  EInvitationReply eInvitationReply;

    public InvitationReply(Long invitationId, EInvitationReply eInvitationReply) {
        this.invitationId = invitationId;
        this.eInvitationReply = eInvitationReply;
    }

    public Long getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(Long invitationId) {
        this.invitationId = invitationId;
    }

    public EInvitationReply geteInvitationReply() {
        return eInvitationReply;
    }

    public void seteInvitationReply(EInvitationReply eInvitationReply) {
        this.eInvitationReply = eInvitationReply;
    }
}
