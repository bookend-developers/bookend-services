package com.bookend.bookclubservice.payload.request;

public class NewClubRequest {

    private String clubName;
    private String description;
    private String username;
    private boolean privatee;

    public NewClubRequest() {
    }

    public NewClubRequest(String clubName, String description, String username, boolean privatee) {
        this.clubName = clubName;
        this.description = description;
        this.username = username;
        this.privatee = privatee;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPrivatee() {
        return privatee;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPrivatee(boolean privatee) {
        this.privatee = privatee;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}