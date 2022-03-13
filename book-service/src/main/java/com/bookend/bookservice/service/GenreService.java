package com.bookend.bookservice.service;

import com.bookend.bookservice.exception.AlreadyExist;
import com.bookend.bookservice.exception.MandatoryFieldException;
import com.bookend.bookservice.exception.NotFoundException;
import com.bookend.bookservice.model.Genre;

import java.util.List;
/**
 * BS-GSC stands for BookService-GenreServiceClass
 * SM stands for ServiceMethod
 */
public interface GenreService {
    /**
     * BS-GSC-1 (SM_40)
     */
    Genre findByGenre(String genre);
    /**
     * BS-GSC-2 (SM_41)
     */
    Genre addNewGenre(String genre) throws AlreadyExist, MandatoryFieldException;
    /**
     * BS-GSC-3 (SM_42)
     */
    List<Genre> findAll();
    /**
     * BS-GSC-4 (SM_43)
     */
    Genre findById(String id) throws NotFoundException;
    /**
     * BS-GSC-5 (SM_44)
     */
    Genre update(Genre genre) throws MandatoryFieldException, NotFoundException, AlreadyExist;
}
