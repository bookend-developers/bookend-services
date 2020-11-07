package com.bookclupservice.bookclubservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "members")
public class Member {

    @Id
    private Long id;

    private String userName;

    @OneToMany(mappedBy="owner")
    private List<Club> ownedClubs;

    @ManyToMany(mappedBy = "members")
    @JsonIgnore
    private List<Club> clubs;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Club> getClubs() {
        return clubs;
    }

    public void setClubs(List<Club> clubs) {
        this.clubs = clubs;
    }

}
