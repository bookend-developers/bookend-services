package com.bookend.shelfservice.service;

import com.bookend.shelfservice.exception.MandatoryFieldException;
import com.bookend.shelfservice.exception.NotFoundException;
import com.bookend.shelfservice.exception.ShelfNotFound;
import com.bookend.shelfservice.model.Shelf;
import com.bookend.shelfservice.model.ShelfsBook;
import com.bookend.shelfservice.payload.ShelfRequest;

import java.util.List;

public interface ShelfService {
    Shelf getById(Long id) throws ShelfNotFound;
    Shelf saveOrUpdate(ShelfRequest shelfRequest, String username) throws MandatoryFieldException;
    List<Shelf> findShelvesByUsername(String username);
    void deleteShelf(Shelf shelfID) throws NotFoundException;
    List<ShelfsBook> getBooks(Long id) throws ShelfNotFound;

}
