package com.accountservice.service;

import com.accountservice.model.Book;

public interface BookService {
    Book getById(String id);

    public Book saveOrUpdate(Book book);
}
