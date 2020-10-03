package com.bookend.shelfservice.service;

import com.bookend.shelfservice.model.Book;

public interface BookService {
    Book getById(String id);

    public Book saveOrUpdate(Book book);
}
