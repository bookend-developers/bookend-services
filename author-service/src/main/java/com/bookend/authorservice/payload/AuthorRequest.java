package com.bookend.authorservice.payload;

import com.bookend.authorservice.model.Book;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

public class AuthorRequest {
    private String name;
    private List<Book> bookList;
    private String biography;
    private String birthDate;
    private String dateOfDeath;

    public AuthorRequest() {
    }

    public AuthorRequest(String name, String biography, String birthDate, String dateOfDeath) {
        this.name = name;
        this.bookList = new ArrayList<>();
        this.biography = biography;
        this.birthDate = birthDate;
        this.dateOfDeath = dateOfDeath;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public String getBiography() {
        return biography;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getDateOfDeath() {
        return dateOfDeath;
    }
}
