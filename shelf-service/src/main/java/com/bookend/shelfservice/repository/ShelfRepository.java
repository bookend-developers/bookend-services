package com.bookend.shelfservice.repository;

import com.bookend.shelfservice.model.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long> {

    Shelf findShelfById(Long id);
    List<Shelf> findShelvesByUsername(String username);

}
