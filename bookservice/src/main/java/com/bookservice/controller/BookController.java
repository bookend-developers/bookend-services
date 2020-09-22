package com.bookservice.controller;

import com.bookservice.model.Book;
import com.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private BookService bookService;
    @Autowired
    public void setBookService(BookService bookService){
        this.bookService=bookService;
    }
    @GetMapping("/book/{bookid}")
    public Book getBookInfo(@PathVariable("bookid") String bookId) {
        return bookService.getById(Long.valueOf(bookId));

    }
}
