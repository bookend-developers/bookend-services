package com.bookend.authorservice.controller;

import com.bookend.authorservice.exception.AuthorAlreadyExists;
import com.bookend.authorservice.exception.MandatoryFieldException;
import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.payload.AuthorRequest;
import com.bookend.authorservice.service.AuthorService;
import com.bookend.authorservice.service.BookService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
            @ApiResponse(code = 400, message = "The way you are trying to add author is not accepted or author already exists.")
    }
    )
    @PostMapping("/new")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Author newAuthor(@RequestBody AuthorRequest author)  {


        Author addedAuthor = null;
        try {
            addedAuthor = authorService.save(author);
        } catch (AuthorAlreadyExists  authorAlreadyExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,authorAlreadyExists.getMessage());
        } catch (MandatoryFieldException mandatoryFieldException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, mandatoryFieldException.getMessage());
        }

        return addedAuthor;

    }


}
