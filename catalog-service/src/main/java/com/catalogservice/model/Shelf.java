package com.catalogservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Shelf {
    private Long id;
    private String shelfname;
    private String username;
    private List<ShelfsBook> books;

    public List<ShelfsBook> getBooks() {
        return books;
    }

    public void setBooks(List<ShelfsBook> books) {
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

    public void setUsername(String username) {
        this.username = username;
    }

    public Shelf() {
    }

    public Shelf(String shelfname, String username){
        this.shelfname = shelfname;
        this.username=username;
        this.books = new ArrayList<ShelfsBook>();

    }
}
