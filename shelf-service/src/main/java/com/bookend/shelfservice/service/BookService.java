package com.bookend.shelfservice.service;

import com.bookend.shelfservice.model.ShelfsBook;

public interface BookService {
    ShelfsBook getById(String id);

    public ShelfsBook saveOrUpdate(ShelfsBook shelfsBook);
}
