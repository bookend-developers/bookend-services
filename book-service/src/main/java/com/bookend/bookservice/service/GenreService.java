package com.bookend.bookservice.service;

import com.bookend.bookservice.model.Genre;

public interface GenreService {
    Genre findByGenre(String genre);
    Genre addNewGenre(String genre);
}
