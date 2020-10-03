package com.bookend.shelfservice.model;

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

    private String username;
    @OneToMany(mappedBy = "shelf")
    private List<ShelfsBook> shelfsBooks;

    public List<ShelfsBook> getShelfsBooks() {
        return shelfsBooks;
    }

    public void setShelfsBooks(List<ShelfsBook> shelfsBooks) {
        this.shelfsBooks = shelfsBooks;
    }

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

    public Shelf() {
    }

    public Shelf(String shelfname, String username) {
        this.shelfname = shelfname;
        this.username = username;
        this.shelfsBooks = new ArrayList<ShelfsBook>();

    }
}
