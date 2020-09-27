package com.bookend.bookservice.service;

import com.bookend.bookservice.model.Book;

import java.util.List;

public interface BookService {
    Book getById(String id);

    Book saveOrUpdate(Book book);
    List<Book> getAll();

}
