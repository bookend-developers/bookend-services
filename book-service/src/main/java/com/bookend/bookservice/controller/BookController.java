package com.bookend.bookservice.controller;

import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.service.BookService;
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

    @ApiOperation(value = "Get the book by Id", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved book"),
            @ApiResponse(code = 401, message = "You are not authorized to view the book"),
            @ApiResponse(code = 404, message = "Book is not found.")
    })
    @GetMapping("/{bookid}")
    public Map<String,String> getBookInfo(@PathVariable("bookid") String bookId,OAuth2Authentication auth ) {
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        String accessToken = details.getTokenValue();
        Map<String,String> fullBook = bookService.getFullBookById(bookId,accessToken);
        if(fullBook==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The book does not exist.");
        }
        return fullBook;

    }
    @ApiOperation(value = "Search book or get all books", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved book list"),
            @ApiResponse(code = 401, message = "You are not authorized to search book"),
            @ApiResponse(code = 404, message = "Book is not found for given title.")
    })
    @GetMapping("")
    public List<Book> search(@RequestParam(required = false) String title
            ,@RequestParam(required = false) String genre){
        List<Book> books = new ArrayList<Book>();

        if(title == null){
            bookService.getAll().forEach(books::add);

        }
        else {
            bookService.search(title).forEach(books::add);
            if(books==null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"There is no match for title.");
            }
        }
        if(genre!=null){
            books = books.stream().filter(book ->
                    book.getGenre().toString().equals(genre))
                    .collect(Collectors.toList());
        }

        return books;
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
        return bookService.findByAuthor(authorId);
    }


}
