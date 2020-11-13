package com.ratecommentservice.service;

import com.ratecommentservice.model.Book;

public interface BookService {
    Book save(Book book);
    Book findBookByBookID( String bookid);
}
