package com.bookend.bookservice.repository;

import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.model.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository  extends MongoRepository<Genre, String> {
    Genre findByGenre(String genre);
    Genre findGenreById(String id);
}
