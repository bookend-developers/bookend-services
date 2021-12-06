package com.bookend.bookclubservice.payload;

public class MailRequest {

    private Long id;

    private String subject;

    private String text;

    public MailRequest(Long id, String subject, String text) {
        this.id = id;
        this.subject = subject;
        this.text = text;
    }

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
