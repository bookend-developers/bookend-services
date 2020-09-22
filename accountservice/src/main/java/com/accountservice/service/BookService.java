package com.accountservice.service;

import com.accountservice.model.Book;

public interface BookService {
    Book getById(Long id);

    public Book saveOrUpdate(Book book);
}
