package com.bookend.shelfservice.payload;

import org.springframework.data.annotation.Id;

public class GenreMessage {

    private String id;
    private String genre;



    public GenreMessage() {
    }

    public GenreMessage(String id, String genre) {
        this.id = id;
        this.genre = genre;
    }

    public GenreMessage(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getId() {
        return id;
    }


}

