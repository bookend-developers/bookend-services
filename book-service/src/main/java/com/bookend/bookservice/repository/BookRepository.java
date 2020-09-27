package com.bookend.bookservice.repository;

import com.bookend.bookservice.model.Book;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    Book findBookById(String id);
}
