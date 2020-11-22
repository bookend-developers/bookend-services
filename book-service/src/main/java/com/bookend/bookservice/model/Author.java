package com.bookend.bookservice.model;



import java.util.ArrayList;
import java.util.List;


public class Author {

    private String id;
    private String name;


    private String biography;
    private String  birthDate;
    private String dateOfDeath;

    public Author() {
    }

    public Author(String id, String name, String biography, String birthDate, String dateOfDeath) {
        this.id = id;
        this.name = name;

        this.biography = biography;
        this.birthDate = birthDate;
        this.dateOfDeath = dateOfDeath;
    }

    public void setId(String id) {
        this.id = id;
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


}
