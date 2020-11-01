package com.bookend.authorservice.controller;

import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.model.Book;
import com.bookend.authorservice.payload.AuthorRequest;
import com.bookend.authorservice.service.AuthorService;
import com.bookend.authorservice.service.BookService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@RestController
@RequestMapping("/api/author/admin")
public class AdminController {
    private AuthorService authorService;
    private BookService bookService;
    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService){
        this.authorService=authorService;
    }
    @ApiOperation(value = "Add new author", response = Author.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added author"),
            @ApiResponse(code = 401, message = "You are not authorized to add the resource"),
            @ApiResponse(code = 400, message = "The way you are trying to add author is not accepted.")
    }
    )
    @PostMapping("/new")
    public Author newAuthor(@RequestBody AuthorRequest author)  {

        Author newAuthor = new Author();
        if(author.getName()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Author name field cannot be empty.");
        }
        newAuthor.setName(author.getName());
        newAuthor.setBiography(author.getBiography());

        newAuthor.setBirthDate(LocalDate.parse(author.getBirthDate(), DateTimeFormatter.ofPattern("d-MMM-yyyy", Locale.US)));

        if(author.getDateOfDeath()==null){
            newAuthor.setDateOfDeath(null);
        }else{
            newAuthor.setDateOfDeath(LocalDate.parse(author.getDateOfDeath(), DateTimeFormatter.ofPattern("d-MMM-yyyy", Locale.US)));
        }
        return authorService.saveOrUpdate(newAuthor);

    }

   //TODO kafka listener will delete book info if the book service deletes book
}
