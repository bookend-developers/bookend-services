package com.bookend.bookservice.controller;

import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    private BookService bookService;
    @Autowired
    public void setBookService(BookService bookService){
        this.bookService=bookService;
    }
    @GetMapping("/book/{bookid}")
    public Book getBookInfo(@PathVariable("bookid") String bookId) {
        return bookService.getById(bookId);

    }
    @GetMapping("/book")
    public List<Book> search(@RequestParam(required = false) String title){
        List<Book> books = new ArrayList<Book>();
        if(title == null){
            bookService.getAll().forEach(books::add);
        }
        else {
            bookService.search(title).forEach(books::add);
        }

        return books;
    }



    @PostMapping("/admin/book/new")
    public Book newBook(@RequestBody Book book){

        return bookService.saveOrUpdate(new Book(book.getPage()
                                                    ,book.getGenre()
                                                    ,book.getDescription()
                                                    ,book.getBookName()
                                                    ,book.getAuthor()));
    }
}
