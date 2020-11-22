package com.bookend.authorservice.service;

import com.bookend.authorservice.model.Book;

public interface BookService {
    Book findByBookid(String bookId);
    Book save(Book book);
    void deleteByBookId(String bookId);
}
