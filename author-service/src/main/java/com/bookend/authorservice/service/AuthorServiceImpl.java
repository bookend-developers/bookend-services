package com.bookend.authorservice.service;

import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Author> authors = authorRepository.findByName(author.getName());
        if(authors.size()!=0){
            List<Author> filteredByBirth = authors.stream()
                    .filter(a -> a.getBirthDate().isEqual(author.getBirthDate()))
                    .collect(Collectors.toList());
            if(filteredByBirth.size()!=0){
                if(!author.getDateOfDeath().equals("")){
                    List<Author> filteredByDeath = filteredByBirth.stream()
                            .filter(auth -> auth.getDateOfDeath().isEqual(author.getDateOfDeath()))
                            .collect(Collectors.toList());
                    if(filteredByDeath.size()!=0){
                        return null;
                    }
                }
            }
        }

        return authorRepository.save(author);
    }

    @Override
    public List<Author> getAll() {


        List<Author> authors = authorRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return authors;
    }

    @Override
    public List<Author> search(String title) {
        return authorRepository.findByNameContainingIgnoreCase(title);
    }
}