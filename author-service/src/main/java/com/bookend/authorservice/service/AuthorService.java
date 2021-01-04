package com.bookend.authorservice.service;

import com.bookend.authorservice.exception.AuthorAlreadyExists;
import com.bookend.authorservice.exception.MandatoryFieldException;
import com.bookend.authorservice.exception.AuthorNotFound;
import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.payload.AuthorRequest;

import java.util.List;

public interface AuthorService {
    Author getById(String id) throws AuthorNotFound;
    Author update(Author author);
    Author save(AuthorRequest request) throws AuthorAlreadyExists, MandatoryFieldException;
    List<Author> getAll();
    List<Author> search(String title);

}