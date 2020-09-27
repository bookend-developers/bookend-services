package com.accountservice.service;

import com.accountservice.model.Book;
import com.accountservice.model.Shelf;

import java.util.List;

public interface ShelfService {
    Shelf getById(Long id);

    Shelf saveOrUpdate(Shelf shelf);
    List<Shelf> findShelvesByAccountID(Long accountID);
}
