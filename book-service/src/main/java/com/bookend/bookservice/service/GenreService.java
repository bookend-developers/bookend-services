package com.bookend.bookservice.service;

import com.bookend.bookservice.exception.AlreadyExist;
import com.bookend.bookservice.exception.MandatoryFieldException;
import com.bookend.bookservice.exception.NotFoundException;
import com.bookend.bookservice.model.Genre;

import java.util.List;

public interface GenreService {
    Genre findByGenre(String genre);
    Genre addNewGenre(String genre) throws AlreadyExist, MandatoryFieldException;
    List<Genre> findAll();
    Genre findById(String id) throws NotFoundException;
    Genre update(Genre genre) throws MandatoryFieldException, NotFoundException, AlreadyExist;
}
