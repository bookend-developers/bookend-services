package com.ratecommentservice.service;

import com.ratecommentservice.model.Book;

import java.util.List;

public interface BookService {
    Book save(Book book);
    Book findBookByBookID( String bookid);
    List<String> findAll();
}
