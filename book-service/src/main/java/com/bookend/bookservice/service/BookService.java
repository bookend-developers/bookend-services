package com.bookend.bookservice.service;

import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.payload.BookRequest;

import java.util.List;
import java.util.Map;

public interface BookService {
    Book getById(String id);
    Book save(BookRequest bookRequest);
    Book update(Book book);
    List<Book> getBooksofShelf(Long shelfID,String accessToken);
    List<Book> getAll();
    List<Book> findByAuthor(String author);
    List<Book> search(String title,String genre,boolean rateSort,boolean commentSort,String accessToken);
    void delete(String bookId);
    List<Book> findBookByVerifiedIsFalse();

}
