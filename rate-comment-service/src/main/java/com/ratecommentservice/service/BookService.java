package com.ratecommentservice.service;

import com.ratecommentservice.exception.BookNotFound;
import com.ratecommentservice.model.Book;

import java.util.List;

public interface BookService {
    Book save(Book book);
    Book findBookByBookID( String bookid) throws BookNotFound;
    List<String> findAll();
}
