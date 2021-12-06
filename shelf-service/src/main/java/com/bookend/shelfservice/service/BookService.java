package com.bookend.shelfservice.service;

import com.bookend.shelfservice.exception.AlreadyExists;
import com.bookend.shelfservice.exception.NotFoundException;
import com.bookend.shelfservice.exception.ShelfsBookNotFound;
import com.bookend.shelfservice.model.Shelf;
import com.bookend.shelfservice.model.ShelfsBook;
import com.bookend.shelfservice.payload.BookRequest;
/**
 * SS-BSC stands for ShelfService-BookServiceClass
 * SM stands for ServiceMethod
 */
public interface BookService {
    /**
     * SS-BSC-1 (SM_78)
     */
    ShelfsBook getById(String id) throws ShelfsBookNotFound;
    /**
     * SS-BSC-2 (SM_79)
     */
    ShelfsBook saveOrUpdate(BookRequest book, Shelf shelf) throws AlreadyExists;
    /**
     * SS-BSC-3 (SM_80)
     */
    void delete(String bookId,String shelfID) throws NotFoundException;
    /**
     * SS-BSC-4 (SM_81)
     */
    void deleteFromShelves(String bookid) throws NotFoundException;

}
