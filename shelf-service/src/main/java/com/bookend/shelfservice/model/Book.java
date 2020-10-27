package com.bookend.shelfservice.model;

public class Book {

    private String id;
    private Integer page;

    private BookGenre genre;

    private String description;


    private String bookName;
    private String author;

    public Book() {
    }

    public Book(String id, Integer page, BookGenre genre, String description, String bookName, String author) {
        this.id = id;
        this.page = page;
        this.genre = genre;
        this.description = description;
        this.bookName = bookName;
        this.author = author;
    }
}
