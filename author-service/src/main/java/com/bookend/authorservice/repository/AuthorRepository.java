package com.bookend.authorservice.repository;

import com.bookend.authorservice.model.Author;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {
    Author findAuthorById(String id);
    List<Author> findAll(Sort sort);
    //List<Author> findAllOrderByName();
    List<Author> findByNameContainingIgnoreCase(String title);
    List<Author> findByName(String name);
}
