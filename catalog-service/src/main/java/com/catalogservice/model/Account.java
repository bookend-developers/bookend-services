package com.catalogservice.model;

import java.util.List;

public class Account {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;

    private List<Shelf> shelfs;

    public Long getId() {
        return id;
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

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public List<Shelf> getShelfs() {
        return shelfs;
    }

    public void setShelfs(List<Shelf> shelfs) {
        this.shelfs = shelfs;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
