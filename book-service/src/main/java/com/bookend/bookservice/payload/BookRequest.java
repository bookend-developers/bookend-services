package com.bookend.bookservice.payload;


import java.util.List;

public class BookRequest {


    private Integer page;

    private String genre;

    private String description;
    private Boolean isVerified;

    private String bookName;
    private String author;
    private String authorid;
    private String isbn;

    public BookRequest() {
    }

    public BookRequest(Integer page, String genre, String description, Boolean isVerified, String bookName, String author, String authorid, String ISBN) {
        this.page = page;
        this.genre = genre;
        this.description = description;
        this.isVerified = isVerified;
        this.bookName = bookName;
        this.author = author;
        this.authorid = authorid;
        this.isbn = ISBN;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
