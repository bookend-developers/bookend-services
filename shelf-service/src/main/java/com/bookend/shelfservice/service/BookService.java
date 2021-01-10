package com.bookend.shelfservice.service;

import com.bookend.shelfservice.exception.NotFoundException;
import com.bookend.shelfservice.exception.ShelfsBookNotFound;
import com.bookend.shelfservice.model.ShelfsBook;

public interface BookService {
    ShelfsBook getById(String id) throws ShelfsBookNotFound;

    ShelfsBook saveOrUpdate(ShelfsBook shelfsBook);
    void delete(String bookId,String shelfID) throws NotFoundException;
    void deleteFromShelves(String bookid) throws NotFoundException;

}
