package com.bookend.shelfservice.repository;

import com.bookend.shelfservice.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {
    Tag findByTag(String tag);
    Tag findTagById(String id);
}
