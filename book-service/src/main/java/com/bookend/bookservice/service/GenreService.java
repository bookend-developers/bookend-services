package com.bookend.bookservice.service;

import com.bookend.bookservice.model.Genre;

import java.util.List;

public interface GenreService {
    Genre findByGenre(String genre);
    Genre addNewGenre(String genre);
    List<Genre> findAll();
    Genre findById(String id);
    Genre update(Genre genre);
}
