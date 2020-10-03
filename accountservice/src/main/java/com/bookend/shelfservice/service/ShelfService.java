package com.bookend.shelfservice.service;

import com.bookend.shelfservice.model.Shelf;

import java.util.List;

public interface ShelfService {
    Shelf getById(Long id);

    Shelf saveOrUpdate(Shelf shelf);
    List<Shelf> findShelvesByUsername(String username);
}
