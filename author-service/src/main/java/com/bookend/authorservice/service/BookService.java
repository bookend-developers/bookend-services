package com.bookend.authorservice.service;

import com.bookend.authorservice.exception.MandatoryFieldException;
import com.bookend.authorservice.exception.NotFoundException;
import com.bookend.authorservice.model.Book;

public interface BookService {
    Book findByBookid(String bookId) throws NotFoundException;
    Book save(Book book) throws MandatoryFieldException;
    void deleteByBookId(String bookId) throws NotFoundException;
}
