package com.bookend.bookservice.repository;

import com.bookend.bookservice.model.Book;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    Book findBookById(String id);
    List<Book> findByBookNameContainingIgnoreCase(String bookname);
    List<Book> findByAuthor(String author);
}
