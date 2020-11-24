package com.bookend.bookservice.repository;

import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.model.BookListByComment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookListByCommentRepo extends MongoRepository<BookListByComment, String> {
}
