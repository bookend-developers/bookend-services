package com.bookend.authorservice.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
public class Book {
    @Id

    private String id;
    private Integer page;
    //TODO make it enum
    private String genre;

    private String description;

    private String bookName;
    private String author;

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



    public Book() {
    }

    public Book(Integer page, String genre, String description, String bookName, String author) {
        this.page = page;
        this.genre = genre;
        this.description = description;
        this.bookName = bookName;
        this.author = author;
    }
}
