package com.bookend.authorservice.service;

import com.bookend.authorservice.exception.AuthorAlreadyExists;
import com.bookend.authorservice.exception.MandatoryFieldException;
import com.bookend.authorservice.exception.NotFoundException;
import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.payload.AuthorRequest;

import java.util.List;
import java.util.Map;

public interface AuthorService {
    Author getById(String id) throws NotFoundException;
    Author update(Map<String,String> msg) throws NotFoundException, MandatoryFieldException;
    Author save(AuthorRequest request) throws AuthorAlreadyExists, MandatoryFieldException;
    List<Author> getAll();
    List<Author> search(String title);

}