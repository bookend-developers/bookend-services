package com.bookend.shelfservice.service;

import com.bookend.shelfservice.model.Shelf;
import com.bookend.shelfservice.payload.ShelfRequest;

import java.util.List;

public interface ShelfService {
    Shelf getById(Long id);

    Shelf saveOrUpdate(ShelfRequest shelfRequest, String username);
    List<Shelf> findShelvesByUsername(String username);
    void deleteShelf(Shelf shelfID);
    List<String> getBooks(Long id);
}
