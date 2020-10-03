package com.catalogservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class ShelfsBook {

    private Long id;

    private String bookID;
    private Shelf shelf;

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public ShelfsBook() {
    }

    public ShelfsBook(String bookID, Shelf shelf) {
        this.bookID = bookID;
        this.shelf = shelf;
    }
}
