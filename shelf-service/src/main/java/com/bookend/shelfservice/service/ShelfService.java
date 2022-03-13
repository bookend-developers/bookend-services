package com.bookend.shelfservice.service;

import com.bookend.shelfservice.exception.AlreadyExists;
import com.bookend.shelfservice.exception.MandatoryFieldException;
import com.bookend.shelfservice.exception.NotFoundException;
import com.bookend.shelfservice.exception.ShelfNotFound;
import com.bookend.shelfservice.model.Shelf;
import com.bookend.shelfservice.model.ShelfsBook;
import com.bookend.shelfservice.payload.ShelfRequest;

import java.util.List;
/**
 * SS-SSC stands for ShelfService-ShelfServiceClass
 * SM stands for ServiceMethod
 */
public interface ShelfService {
    /**
     * SS-SSC-1 (SM_79)
     */
    Shelf getById(Long id) throws ShelfNotFound;
    /**
     * SS-SSC-2 (SM_80)
     */
    Shelf saveOrUpdate(ShelfRequest shelfRequest, String username) throws MandatoryFieldException, AlreadyExists;
    /**
     * SS-SSC-3 (SM_81)
     */
    List<Shelf> findShelvesByUsername(String username);
    /**
     * SS-SSC-4 (SM_82)
     */
    void deleteShelf(Shelf shelfID) throws NotFoundException;
    /**
     * SS-SSC-5 (SM_83)
     */
    List<ShelfsBook> getBooks(Long id) throws ShelfNotFound;

}
