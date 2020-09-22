package com.accountservice.service;

import com.accountservice.model.Book;
import com.accountservice.model.Shelf;

public interface ShelfService {
    Shelf getById(Long id);

    Shelf saveOrUpdate(Shelf shelf);
}
