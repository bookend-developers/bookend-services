package com.bookend.shelfservice.repository;

import com.bookend.shelfservice.model.Shelf;
import com.bookend.shelfservice.model.ShelfsBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository  extends JpaRepository<ShelfsBook,Long> {
    ShelfsBook findBookById(Long id);
    List<ShelfsBook> findShelfsBookByBookID(String id);
    List<ShelfsBook> findShelfsBookByShelf(Shelf shelf);
    void deleteShelfsBookByBookIDAndShelf_Id(String bookID,Long shelfId);
}
