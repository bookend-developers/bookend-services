package com.bookend.authorservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "authors")
public class Author {
    @Id

    private String id;
    private String name;
    private List<Book> bookList;
    private String biography;
    private String birthDate;
    private String dateOfDeath;

    public Author() {
    }

    public Author(String name, String biography, String birthDate, String dateOfDeath) {
        this.name = name;
        this.biography=biography;
        this.birthDate=birthDate;
        this.dateOfDeath=dateOfDeath;
        this.bookList=new ArrayList<Book>();
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDateOfDeath() { return dateOfDeath; }

    public void setDateOfDeath(String dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
