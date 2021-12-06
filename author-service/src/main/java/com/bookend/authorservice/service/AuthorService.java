package com.bookend.authorservice.service;

import com.bookend.authorservice.exception.AuthorAlreadyExists;
import com.bookend.authorservice.exception.MandatoryFieldException;
import com.bookend.authorservice.exception.NotFoundException;
import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.payload.AuthorRequest;

import java.util.List;
import java.util.Map;

/**
 * AS-ASC stands for AuthorService-AuthorServiceClass
 * SM stands for ServiceMethods
 */
public interface AuthorService {
    /**
    * AS-ASC-1 (SM_1)
     */
    Author getById(String id) throws NotFoundException;
    /**
     * AS-ASC-2 (SM_2)
     */
    Author update(Map<String,String> msg) throws NotFoundException, MandatoryFieldException;
    /**
     * AS-ASC-3 (SM_3)
     */
    Author save(AuthorRequest request) throws AuthorAlreadyExists, MandatoryFieldException;
    /**
     * AS-ASC-4 (SM_4)
     */
    List<Author> getAll();
    /**
     * AS-ASC-5 (SM_5)
     */
    List<Author> search(String title);

}