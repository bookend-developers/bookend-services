package com.bookend.authorizationserver.payload;

import javax.persistence.Column;

public class SignUpRequest {

    private String username;
    private String password;
    private String email;

    public SignUpRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public SignUpRequest() {

    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
