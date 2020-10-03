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
        authorRepository.save(author);
        return author;
    }

    @Override
    public List<Author> getAll() {
        List<Author> a = authorRepository.findAll();
        return authorRepository.findAll();
    }
}