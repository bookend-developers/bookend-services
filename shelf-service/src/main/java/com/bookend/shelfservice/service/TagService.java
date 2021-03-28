package com.bookend.shelfservice.service;

import com.bookend.shelfservice.exception.AlreadyExists;
import com.bookend.shelfservice.exception.TagNotFound;
import com.bookend.shelfservice.model.Tag;
import com.bookend.shelfservice.payload.GenreMessage;

import java.util.List;

public interface TagService {

    List<Tag> allTag();
    Tag findByID(String id) throws TagNotFound;
    Tag save(GenreMessage msg) throws AlreadyExists;
}
