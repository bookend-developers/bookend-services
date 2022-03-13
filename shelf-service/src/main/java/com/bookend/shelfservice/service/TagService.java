package com.bookend.shelfservice.service;

import com.bookend.shelfservice.exception.AlreadyExists;
import com.bookend.shelfservice.exception.TagNotFound;
import com.bookend.shelfservice.model.Tag;
import com.bookend.shelfservice.payload.GenreMessage;

import java.util.List;
/**
 * SS-TSC stands for ShelfService-TagServiceClass
 * SM stands for ServiceMethod
 */
public interface TagService {
    /**
     * SS-TSC-1 (SM_84)
     */
    List<Tag> allTag();
    /**
     * SS-TSC-2 (SM_85)
     */
    Tag findByID(String id) throws TagNotFound;
    /**
     * SS-TSC-3 (SM_86)
     */
    Tag save(GenreMessage msg) throws AlreadyExists;
}
