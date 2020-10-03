package com.bookend.shelfservice.repository;

import com.bookend.shelfservice.model.ShelfsBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository  extends JpaRepository<ShelfsBook,Long> {
    ShelfsBook findBookById(String id);
}
