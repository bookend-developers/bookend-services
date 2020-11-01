package com.bookend.shelfservice.service;

import com.bookend.shelfservice.model.ShelfsBook;

public interface BookService {
    ShelfsBook getById(String id);

    ShelfsBook saveOrUpdate(ShelfsBook shelfsBook);
    void delete(String bookId,String shelfID);
    void deleteFromShelves(String bookid);

}
