package com.bookend.shelfservice.controller;

import com.bookend.shelfservice.model.ShelfsBook;
import com.bookend.shelfservice.model.Shelf;
import com.bookend.shelfservice.service.BookService;
import com.bookend.shelfservice.service.ShelfService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("/api/shelf")
public class ShelfController {

    private BookService bookService;
    private ShelfService shelfService;


    @Autowired
    public void setBookService(BookService bookService){
        this.bookService=bookService;
    }

    @Autowired
    public void setShelfService(ShelfService shelfService){ this.shelfService=shelfService;}

    @ApiOperation(value = "Add new book to shelf", response = ShelfsBook.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added book"),
            @ApiResponse(code = 401, message = "You are not authorized to add the resource"),
            @ApiResponse(code = 400, message = "The book is already added this shelf.")
    }
    )
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
    @ApiOperation(value = "Create new shelf", response = Shelf.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added shelf"),
            @ApiResponse(code = 401, message = "You are not authorized to create the resource"),
            @ApiResponse(code = 400, message = "The shelf name is already in use.")
    }
    )
    @PostMapping("/new")
    public Shelf newShelf(@RequestParam(name = "name") String shelfname,
                          OAuth2Authentication auth){
        Shelf newShelf =shelfService.saveOrUpdate(new Shelf(shelfname,auth.getName()));
        if(newShelf== null){

           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Shelfname is already in use.");
        }
        return newShelf ;

    }
    @ApiOperation(value = "Get book ids in the shelf", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource")
    }
    )
    @GetMapping("/{shelfid}")
    public List<String> getBooks(@PathVariable("shelfid")  String shelfID){


        return shelfService.getBooks(Long.valueOf(shelfID));

    }
    @ApiOperation(value = "Get user's shelves", response = Shelf.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource")
    }

    )
    @GetMapping("/user/{username}")
    public List<Shelf> getShelves(@PathVariable("username") String username){

        return shelfService.findShelvesByUsername(username);
    }
    @ApiOperation(value = "Get current user's shelves", response = Shelf.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource")
    }

    )
    @GetMapping("/user")
    public List<Shelf> getUserShelves( OAuth2Authentication auth){

        return shelfService.findShelvesByUsername(auth.getName());
    }
    @ApiOperation(value = "Delete the shelf")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted shelf"),
            @ApiResponse(code = 401, message = "You are not authorized to delete the resource"),
            @ApiResponse(code = 403, message = "The action is forbidden.")
    }
    )
    @DeleteMapping("/delete/{shelfid}")
    public void deleteShelf(@PathVariable("shelfid")  String shelfID,OAuth2Authentication auth){
        Shelf shelf = shelfService.getById(Long.valueOf(shelfID));
        if(shelf.getUsername()!=auth.getName()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"The action is forbidden.");
        }
        shelfService.deleteShelf(shelfService.getById(Long.valueOf(shelfID)));
    }
    @ApiOperation(value = "Delete the book from shelves")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted book"),
            @ApiResponse(code = 401, message = "You are not authorized to delete the resource"),
            @ApiResponse(code = 403, message = "The action is forbidden.")
    }
    )
    @DeleteMapping("/delete/{shelfid}/{bookid}")
    public void deleteBook(@PathVariable("bookid") String bookId,
                           @PathVariable("shelfid")  String shelfID,OAuth2Authentication auth){
        Shelf shelf = shelfService.getById(Long.valueOf(shelfID));
        if(shelf.getUsername()!=auth.getName()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"The action is forbidden.");
        }
        bookService.delete(bookId,shelfID);
    }

}
