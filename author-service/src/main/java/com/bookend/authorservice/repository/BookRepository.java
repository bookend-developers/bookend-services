package com.bookend.authorservice.repository;

import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    Book findByBookId(String bookid);
    void deleteByBookId(String bookid);
}
