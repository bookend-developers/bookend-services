package com.bookend.bookservice.payload;

import com.bookend.bookservice.model.BookGenre;

public class BookRequest {


    private Integer page;

    private BookGenre genre;

    private String description;


    private String bookName;
    private String author;

    public BookRequest() {
    }

    public BookRequest(Integer page, BookGenre genre, String description, String bookName, String author) {
        this.page = page;
        this.genre = genre;
        this.description = description;
        this.bookName = bookName;
        this.author = author;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public BookGenre getGenre() {
        return genre;
    }

    public void setGenre(BookGenre genre) {
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
}
