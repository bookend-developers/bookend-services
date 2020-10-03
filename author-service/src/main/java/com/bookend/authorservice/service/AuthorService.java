package com.bookend.authorservice.service;

import com.bookend.authorservice.model.Author;

import java.util.List;

public interface AuthorService {
    Author getById(String id);

    Author saveOrUpdate(Author author);
    List<Author> getAll();

}