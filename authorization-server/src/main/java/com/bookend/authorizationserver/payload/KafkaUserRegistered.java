package com.bookend.authorizationserver.payload;

public class KafkaUserRegistered {

    Long id;
    String userName;
    String mail;

    public KafkaUserRegistered(Long id, String userName, String mail) {
        this.id = id;
        this.userName = userName;
        this.mail = mail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
