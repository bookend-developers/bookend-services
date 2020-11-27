package com.bookend.bookservice.repository;

import com.bookend.bookservice.model.SortedLists;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SortedListRepo extends MongoRepository<SortedLists, String> {
}
