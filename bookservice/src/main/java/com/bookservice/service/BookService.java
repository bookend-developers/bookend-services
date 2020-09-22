package com.bookservice.service;

import com.bookservice.model.Book;

public interface BookService {
    Book getById(Long id);

    public Book saveOrUpdate(Book book);
}
