package com.bookend.bookservice.controller;

import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.service.BookService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            @ApiResponse(code = 401, message = "You are not authorized to add the resource")
    }
    )
    @PostMapping("/new")
    public Book newBook(@RequestBody Book book){

        return bookService.saveOrUpdate(new Book(book.getPage()
                ,book.getGenre()
                ,book.getDescription()
                ,book.getBookName()
                ,book.getAuthor()));
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
