package com.bookend.bookservice.service;

import com.bookend.bookservice.model.Book;

import java.util.List;
import java.util.Map;

public interface BookService {
    Book getById(String id);
    Book saveOrUpdate(Book book);
    Book update(Book book);
    List<Book> getBooksofShelf(Long shelfID,String accessToken);
    List<Book> getAll();
    List<Book> findByAuthor(String author);
    List<Book> search(String title);
    void delete(String bookId);
    List<Book> findBookByVerifiedIsFalse();

}
