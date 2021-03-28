package com.bookend.shelfservice.service;

import com.bookend.shelfservice.exception.AlreadyExists;
import com.bookend.shelfservice.exception.NotFoundException;
import com.bookend.shelfservice.exception.ShelfsBookNotFound;
import com.bookend.shelfservice.model.Shelf;
import com.bookend.shelfservice.model.ShelfsBook;
import com.bookend.shelfservice.payload.BookRequest;

public interface BookService {
    ShelfsBook getById(String id) throws ShelfsBookNotFound;

    ShelfsBook saveOrUpdate(BookRequest book, Shelf shelf) throws AlreadyExists;
    void delete(String bookId,String shelfID) throws NotFoundException;
    void deleteFromShelves(String bookid) throws NotFoundException;

}
