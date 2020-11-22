package com.bookend.shelfservice.service;

import com.bookend.shelfservice.model.Tag;

import java.util.List;

public interface TagService {
    Tag saveAndUpdate(String tag);
    List<Tag> allTag();
    Tag findByID(String id);
    Tag save(Tag tag);
}
