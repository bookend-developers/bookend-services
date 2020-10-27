package com.bookend.shelfservice.service;

import com.bookend.shelfservice.model.Book;
import com.bookend.shelfservice.model.Shelf;

import java.util.List;

public interface ShelfService {
    Shelf getById(Long id);

    Shelf saveOrUpdate(Shelf shelf);
    List<Shelf> findShelvesByUsername(String username);
    void deleteShelf(Shelf shelfID);
    List<Book> getBooks(Long id, String accessToken);
}
