package com.bookend.bookservice.service;

import com.bookend.bookservice.model.Book;

import java.util.List;
import java.util.Map;

public interface BookService {
    Book getById(String id);
    Map<String,String> getFullBookById(String id, String accessToken);
    Book saveOrUpdate(Book book);
    List<Book> getBooksofShelf(Long shelfID,String accessToken);
    List<Book> getAll();
    List<Book> findByAuthor(String author);
    List<Book> search(String title);
    void delete(String bookId);

}
