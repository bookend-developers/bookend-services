package com.bookend.authorizationserver.payload;

import java.time.LocalDate;

public class Profile {


    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String aboutMe;
    private String email;


    public Long getId() {
        return id;

    }

//TODO usernam yap postrequest
    public void setId(Long id) {
        this.id = id;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;

    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public Profile() {
    }


    public Profile(Long id, String username, String email) {
        this.id=id;
        this.username = username;
        this.email = email;
    }

    public Profile(String firstname, String lastname, String username, String aboutMe, String email) {

        this.firstname = firstname;
        this.lastname = lastname;

        this.username = username;
        this.aboutMe = aboutMe;
        this.email = email;
    }
}
