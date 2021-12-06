package com.bookend.bookservice.model;


import com.fasterxml.jackson.annotation.JsonGetter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "books")
public class Book {
    @Id
    private String id;
    private Integer page;
    private String bookName;
    private Genre genre;
    private String description;
    private String author;
    private String authorid;
    private boolean verified;
    private String ISBN;
    private List<Long> comments;
    private Double rate;

    public List<Long> getComments() {
        return comments;
    }

    public void setComments(List<Long> comments) {
        this.comments = comments;
    }
    @JsonGetter
    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
    @JsonGetter
    public boolean getVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getId() {
        return id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public Book() {
        this.comments = new ArrayList<>();
        this.rate = 0.0;
    }
    @JsonGetter
    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Book(Integer page, Genre genre, String description, String bookName, String author, String authorid, boolean verified, String ISBN) {
        this.page = page;
        this.genre = genre;
        this.description = description;
        this.bookName = bookName;
        this.author = author;
        this.authorid = authorid;
        this.verified = verified;
        this.ISBN = ISBN;
        this.comments = new ArrayList<>();
        this.rate = 0.0;
    }
    public Book(String id,Integer page, Genre genre, String description, String bookName, String author, String authorid, boolean verified, String ISBN) {
        this.id = id;
        this.page = page;
        this.genre = genre;
        this.description = description;
        this.bookName = bookName;
        this.author = author;
        this.authorid = authorid;
        this.verified = verified;
        this.ISBN = ISBN;
        this.comments = new ArrayList<>();
        this.rate = 0.0;
    }
}