package com.bookend.authorizationserver.payload;

import java.time.LocalDate;

public class Profile {

    private String firstname;
    private String lastname;
    private String username;

    private String aboutMe;
    private String email;





    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public Profile(String firstname, String lastname, String username, String aboutMe, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.aboutMe = aboutMe;
        this.email = email;
    }
}
