package com.bookend.bookservice.controller;

import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.payload.BookRequest;
import com.bookend.bookservice.service.BookService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/book/admin")
public class AdminController {
    private BookService bookService;
    @Autowired
    public void setBookService(BookService bookService){
        this.bookService=bookService;
    }
    @ApiOperation(value = "Add new book", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added book"),
            @ApiResponse(code = 401, message = "You are not authorized to add the resource"),
            @ApiResponse(code = 400, message = "The way you are trying to add book is not accepted.")
    }
    )
    @PostMapping("/new")
    public Book newBook(@RequestBody BookRequest bookRequest){
        Book newBook= new Book();
        if(bookRequest.getBookName()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Book name field cannot be empty.");
        }
        newBook.setBookName(bookRequest.getBookName());
        if(bookRequest.getAuthor()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Author field cannot be empty.");
        }
        newBook.setAuthor(bookRequest.getAuthor());
        newBook.setDescription(bookRequest.getDescription());
        newBook.setGenre(bookRequest.getGenre());
        newBook.setPage(bookRequest.getPage());

        return bookService.saveOrUpdate(newBook);
    }
    @ApiOperation(value = "Delete the book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted book"),
            @ApiResponse(code = 401, message = "You are not authorized to delete the resource")
    }
    )
    @DeleteMapping("/{bookid}")
    public void delete(@PathVariable("bookid") String bookId){
        bookService.delete(bookId);
        //TODO send message to delete data for this book
    }
}
