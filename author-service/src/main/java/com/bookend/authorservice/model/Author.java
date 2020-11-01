package com.bookend.authorservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.thymeleaf.util.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "authors")
public class Author {
    @Id
    private String id;
    private String name;
    @DBRef
    private List<Book> bookList;
    private String biography;
    private LocalDate  birthDate;
    private LocalDate dateOfDeath;

    public Author() {
        this.bookList=new ArrayList<Book>();
    }

    public Author(String name, String biography, LocalDate birthDate, LocalDate dateOfDeath) {
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


    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(LocalDate dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
