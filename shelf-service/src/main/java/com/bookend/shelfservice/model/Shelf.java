package com.bookend.shelfservice.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name= "shelf")
public class Shelf {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shelfId")
    private Long id;
    private String shelfname;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable
    private List<Tag> tags;
    private String username;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "shelf")

    private List<ShelfsBook> shelfsBooks;

    @JsonGetter("shelfsBook")
    public List<ShelfsBook> getShelfsBooks() {
        return shelfsBooks;
    }


    public void setShelfsBooks(List<ShelfsBook> shelfsBooks) { this.shelfsBooks = shelfsBooks; }

    public Long getId() {
        return id;
    }

    public String getShelfname() {
        return shelfname;
    }

    public void setShelfname(String shelfname) {
        this.shelfname = shelfname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String account) {
        this.username = account;
    }
    @JsonGetter
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Shelf() {
    }

    public Shelf(String shelfname, String username, List<Tag> tags) {
        this.shelfname = shelfname;
        this.username = username;
        this.shelfsBooks = new ArrayList<>();
        this.tags = tags;
    }

    public Shelf(Long id,String shelfname, String username, List<Tag> tags) {
        this.id = id;
        this.shelfname = shelfname;
        this.username = username;
        this.shelfsBooks = new ArrayList<>();
        this.tags = tags;
    }


    public Shelf(String shelfname, String username) {
        this.shelfname = shelfname;
        this.tags = new ArrayList<>();
        this.username = username;
        this.shelfsBooks = new ArrayList<>();
    }
}
