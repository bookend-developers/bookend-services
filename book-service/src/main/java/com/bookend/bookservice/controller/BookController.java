package com.bookend.bookservice.controller;

import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
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

    @GetMapping("/user/shelf/{shelfid}")
    public List<Book> getBooksofShelf(@PathVariable("shelfid") String shelfID, OAuth2Authentication auth){
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        //token
        String accessToken = details.getTokenValue();
        return bookService.getBooksofShelf(Long.valueOf(shelfID),accessToken);
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
