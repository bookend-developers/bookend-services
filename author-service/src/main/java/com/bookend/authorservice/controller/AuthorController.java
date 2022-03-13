package com.bookend.authorservice.controller;

import com.bookend.authorservice.exception.NotFoundException;
import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.model.Book;
import com.bookend.authorservice.service.AuthorService;
import com.bookend.authorservice.service.BookService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
/**
 * AS-AUTHORC stands for AuthorService-AuthorController
 * CM stands for ControllerMethod
 */
@RestController
@RequestMapping("/api/author")
public class AuthorController {

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


    /**
     * AS-AUTHORC-1 (CM_2)
     */
    @ApiOperation(value = "Get Author with given id", response = Author.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved author"),
            @ApiResponse(code = 401, message = "You are not authorized to view author."),
            @ApiResponse(code = 404, message = "Author is not found.")
    })
    @GetMapping("/{authorid}")
    public Author getAuthorInfo(@PathVariable("authorid") String authorId) {
        Author author= null;
        try {
            author = authorService.getById(authorId);
        } catch (NotFoundException notFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, notFoundException.getMessage());
        }

        return author;

    }
    /**
     * AS-AUTHORC-2 (CM_3)
     */
    @ApiOperation(value = "Search author", response = Author.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved author list"),
            @ApiResponse(code = 401, message = "You are not authorized to view authors.")
    })
    @GetMapping("")
    public List<Author> getAllAuthor(@RequestParam(required = false) String title){
        List<Author> authors = new ArrayList<Author>();
        if(title== null){
            authorService.getAll().forEach(authors::add);
        }
        else{
            authorService.search(title).forEach(authors::add);
            if(authors==null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"There is no match for title.");
            }
        }

        return authors;
    }
    /**
     * AS-AUTHORC-3 (CM_4)
     */
    @ApiOperation(value = "Get books Id's of the author", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved book list"),
            @ApiResponse(code = 401, message = "You are not authorized to view books."),
            @ApiResponse(code = 404, message = "Author does not exist.")
    })
    @GetMapping("/books")
    public List<Book> getAuthorBooks(@RequestParam String authorid){
        Author author = null;
        try {
            author = authorService.getById(authorid);
        } catch (NotFoundException notFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, notFoundException.getMessage());
        }
        return author.getBookList();
    }
}
