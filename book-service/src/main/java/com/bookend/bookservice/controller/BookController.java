package com.bookend.bookservice.controller;

import com.bookend.bookservice.exception.AlreadyExist;
import com.bookend.bookservice.exception.MandatoryFieldException;
import com.bookend.bookservice.exception.NotFoundException;
import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.model.Genre;
import com.bookend.bookservice.payload.BookRequest;
import com.bookend.bookservice.service.BookService;
import com.bookend.bookservice.service.GenreService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private BookService bookService;
    @Autowired
    public void setBookService(BookService bookService){
        this.bookService=bookService;
    }
    private GenreService genreService;
    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    @ApiOperation(value = "Get the book by Id", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved book"),
            @ApiResponse(code = 401, message = "You are not authorized to view the book"),
            @ApiResponse(code = 404, message = "Book is not found.")
    })
    @GetMapping("/{bookid}")
    public Book getBook(@PathVariable("bookid") String bookId ) {

        Book book = null;
        try {
            book = bookService.getById(bookId);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
        return book;

    }
    @ApiOperation(value = "Search book or get all books", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved book list"),
            @ApiResponse(code = 401, message = "You are not authorized to search book"),
            @ApiResponse(code = 404, message = "Book is not found for given title.")
    })
    @GetMapping("")
    public List<Book> search(@RequestParam(required = false) String title
            ,@RequestParam(required = false) String genre
            ,@RequestParam(required = false) boolean rateSort
            ,@RequestParam(required = false) boolean commentSort
            , OAuth2Authentication auth){
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        try {
            return bookService.search(title,genre,rateSort,commentSort);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }


    }
    @ApiOperation(value = "Get books in the shelf", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved book list"),
            @ApiResponse(code = 401, message = "You are not authorized to view books.")
    })
    @GetMapping("/user/shelf/{shelfid}")
    public List<Book> getBooksofShelf(@PathVariable("shelfid") String shelfID, OAuth2Authentication auth){
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();

        String accessToken = details.getTokenValue();
        return bookService.getBooksofShelf(Long.valueOf(shelfID),accessToken);
    }
    @ApiOperation(value = "Get books of a specific author", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved book list"),
            @ApiResponse(code = 401, message = "You are not authorized to view books.")
    })
    @GetMapping("/author/{authorid}")
    public List<Book> getBookOfAuthor(@PathVariable("authorid") String authorId){
        try {
            return bookService.findByAuthor(authorId);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
    @ApiOperation(value = "Add new book", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added book"),
            @ApiResponse(code = 401, message = "You are not authorized to add the resource"),
            @ApiResponse(code = 400, message = "The way you are trying to add book is not accepted.")
    }
    )
    @PostMapping("/new")
    public Book userBook(@RequestBody BookRequest bookRequest){
        bookRequest.setVerified(false);
        Book addedBook = null;
        try {
            addedBook = bookService.save(bookRequest);
        } catch (MandatoryFieldException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        } catch (AlreadyExist e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Book already exists.");
        }
        return addedBook;
    }


}
