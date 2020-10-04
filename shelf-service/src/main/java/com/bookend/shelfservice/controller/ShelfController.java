package com.bookend.shelfservice.controller;

import com.bookend.shelfservice.model.ShelfsBook;
import com.bookend.shelfservice.model.Shelf;
import com.bookend.shelfservice.service.BookService;
import com.bookend.shelfservice.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ShelfController {

    private BookService bookService;
    private ShelfService shelfService;


    @Autowired
    public void setBookService(BookService bookService){
        this.bookService=bookService;
    }

    @Autowired
    public void setShelfService(ShelfService shelfService){ this.shelfService=shelfService;}

    @PostMapping("/{shelfid}/{bookid}")
    public ShelfsBook addBookToShelf(@PathVariable("shelfid") String shelfID,
                                     @PathVariable("bookid") String bookID){
        Shelf shelf = shelfService.getById(Long.valueOf(shelfID));
        ShelfsBook shelfsBook = bookService.saveOrUpdate(new ShelfsBook(bookID, shelf));
        if(shelfsBook== null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Book is already added to the Shelf.");
        }
        return shelfsBook;


    }
    @PostMapping("/shelf/new")
    public Shelf newShelf(@RequestParam(name = "name") String shelfname,
                          OAuth2Authentication auth){
        Shelf newShelf =shelfService.saveOrUpdate(new Shelf(shelfname,auth.getName()));
        if(newShelf== null){

           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Shelfname is already in use.");
        }
        return newShelf ;

    }
    @GetMapping("/shelf/{shelfid}")
    public List<String> getBooks(@PathVariable("shelfid")  String shelfID){
        List<String> bookIDs = shelfService.getById(Long.valueOf(shelfID)).getShelfsBooks()
                                                .stream()
                                                        .map(ShelfsBook::getBookID)
                                                        .collect(Collectors.toList());
        return bookIDs;

    }
    @GetMapping("/shelves")
    public List<Shelf> getShelves(OAuth2Authentication auth){

        return shelfService.findShelvesByUsername(auth.getUserAuthentication().getName());
    }

}
