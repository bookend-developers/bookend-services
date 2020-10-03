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
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
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
        this.books = new ArrayList<Book>();

    }
}
