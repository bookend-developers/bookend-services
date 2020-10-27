package com.bookend.authorservice.service;

import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;
    @Autowired
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getById(String id) {
        return authorRepository.findAuthorById(id);
    }



    @Override
    public Author saveOrUpdate(Author author) {

        return authorRepository.save(author);
    }

    @Override
    public List<Author> getAll() {

        return authorRepository.findAll();
    }

    @Override
    public List<Author> search(String title) {
        return authorRepository.findByNameContainingIgnoreCase(title);
    }
}