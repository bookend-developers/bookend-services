package com.bookend.shelfservice.repository;

import com.bookend.shelfservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository  extends JpaRepository<Book,Long> {
    Book findBookById(String id);
}
