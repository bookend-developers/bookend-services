package com.bookend.shelfservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class ShelfsBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "bookId")
    private String bookID;
    @Column(name = "bookName")
    private String bookName;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
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


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public ShelfsBook() {
    }

    public ShelfsBook(String bookID, String bookName, Shelf shelf) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.shelf = shelf;
    }
}
