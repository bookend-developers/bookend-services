package com.bookend.authorservice.controller;

import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {

    private AuthorService authorService;
    @Autowired
    public void setAuthorService(AuthorService authorService){
        this.authorService=authorService;
    }
    @GetMapping("/author/{authorid}")
    public Author getAuthorInfo(@PathVariable("authorid") String authorId) {
        return authorService.getById(authorId);

    }
    @GetMapping("/author/all")
    public List<Author> getAllAuthor(){
        return authorService.getAll();
    }
    @PostMapping("/author/new")
    public Author newAuthor(@RequestBody Author author){

        return authorService.saveOrUpdate(new Author(author.getName()
                ,author.getBiography()
                ,author.getBirthDate()
                ,author.getDateOfDeath()));

    }
}
