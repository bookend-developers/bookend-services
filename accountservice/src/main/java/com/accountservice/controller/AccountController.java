package com.accountservice.controller;

import com.accountservice.model.Account;
import com.accountservice.model.Book;
import com.accountservice.model.Shelf;
import com.accountservice.service.AccountService;
import com.accountservice.service.BookService;
import com.accountservice.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AccountController {
    private AccountService accountService;
    private BookService bookService;
    private ShelfService shelfService;

    @Autowired
    public void setAccountService(AccountService accountService){ this.accountService= accountService;}

    @Autowired
    public void setBookService(BookService bookService){
        this.bookService=bookService;
    }

    @Autowired
    public void setShelfService(ShelfService shelfService){ this.shelfService=shelfService;}

    @PostMapping("/{shelfid}/{bookid}")
    public Book addBookToShelf(@PathVariable("shelfid") String shelfID,
                               @PathVariable("bookid") String bookID){
        Shelf shelf = shelfService.getById(Long.valueOf(shelfID));
        Book book = bookService.saveOrUpdate(new Book(Long.valueOf(bookID), shelf));
        return book;


    }
    @PostMapping("/{userid}/new/shelf")
    public Shelf newShelf(@RequestParam(name = "name") String shelfname,
                          @PathVariable("userid") String userID){
        Account account= accountService.getById(Long.valueOf(userID));
        return shelfService.saveOrUpdate(new Shelf(shelfname,account)) ;

    }
    @GetMapping("/shelf/{shelfid}")
    public List<Long> getBooks(@PathVariable("shelfid")  String shelfID){
        List<Long> bookIDs = shelfService.getById(Long.valueOf(shelfID)).getBooks()
                                                .stream()
                                                        .map(Book::getBookID)
                                                        .collect(Collectors.toList());
        return bookIDs;

    }

}
