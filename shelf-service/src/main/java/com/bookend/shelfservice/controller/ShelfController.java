package com.bookend.shelfservice.controller;

import com.bookend.shelfservice.exception.*;
import com.bookend.shelfservice.model.ShelfsBook;
import com.bookend.shelfservice.model.Shelf;
import com.bookend.shelfservice.model.Tag;
import com.bookend.shelfservice.payload.BookRequest;
import com.bookend.shelfservice.payload.ShelfRequest;
import com.bookend.shelfservice.service.BookService;
import com.bookend.shelfservice.service.ShelfService;
import com.bookend.shelfservice.service.TagService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.swing.text.TabableView;
import java.util.List;
/**
 * SS-SC stands for ShelfService-ShelfController
 * CM stands for ControllerMethod
 */
@RestController
@RequestMapping("/api/shelf")
public class ShelfController {

    private BookService bookService;
    private ShelfService shelfService;
    private TagService tagService;

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setBookService(BookService bookService){
        this.bookService=bookService;
    }

    @Autowired
    public void setShelfService(ShelfService shelfService){ this.shelfService=shelfService;}
    /**
     * SS-SC-1 (CM_53)
     */
    @ApiOperation(value = "Add new book to shelf", response = ShelfsBook.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added book"),
            @ApiResponse(code = 401, message = "You are not authorized to add the resource"),
            @ApiResponse(code = 400, message = "The book is already added this shelf.")
    }
    )
    @PostMapping("/{shelfid}")
    public ShelfsBook addBookToShelf(@PathVariable("shelfid") String shelfID,
                                     @RequestBody BookRequest book) throws ShelfNotFound {

        Shelf shelf = shelfService.getById(Long.valueOf(shelfID));
        ShelfsBook shelfsBook = null;
        try {
            shelfsBook = bookService.saveOrUpdate(book ,shelf);
        } catch (AlreadyExists alreadyExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Book is already added to the Shelf.");
        }
        return shelfsBook;


    }
    /**
     * SS-SC-2 (CM_54)
     */
    @ApiOperation(value = "Create new shelf", response = Shelf.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added shelf"),
            @ApiResponse(code = 401, message = "You are not authorized to create the resource"),
            @ApiResponse(code = 400, message = "The shelf name is already in use.")
    }
    )
    @PostMapping("/new")
    public Shelf newShelf(@RequestBody ShelfRequest shelfRequest,
                          OAuth2Authentication auth) throws MandatoryFieldException {
        Shelf newShelf = null;
        try {
            newShelf = shelfService.saveOrUpdate(shelfRequest,auth.getName());
        } catch (AlreadyExists alreadyExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Shelfname is already in use.");
        }
        return newShelf ;

    }
    /**
     * SS-SC-3 (CM_55)
     */
    @ApiOperation(value = "Get book  in the shelf", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource")
    }
    )
    @GetMapping("/{shelfid}")

    public List<ShelfsBook> getBooks(@PathVariable("shelfid")  String shelfID) {
        try {
            return shelfService.getBooks(Long.valueOf(shelfID));
        } catch (ShelfNotFound shelfNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,shelfNotFound.getMessage());
        }

    }
    /**
     * SS-SC-4 (CM_56)
     */
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
    /**
     * SS-SC-5 (CM_57)
     */
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
    /**
     * SS-SC-6 (CM_58)
     */
    @ApiOperation(value = "Delete the shelf")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted shelf"),
            @ApiResponse(code = 401, message = "You are not authorized to delete the resource"),
            @ApiResponse(code = 403, message = "The action is forbidden.")
    }
    )
    @DeleteMapping("/delete/{shelfid}")
    public void deleteShelf(@PathVariable("shelfid")  String shelfID,OAuth2Authentication auth) throws ShelfNotFound, NotFoundException {
        Shelf shelf = shelfService.getById(Long.valueOf(shelfID));
        if(!shelf.getUsername().equals(auth.getName())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"The action is forbidden.");
        }
        shelfService.deleteShelf(shelfService.getById(Long.valueOf(shelfID)));
    }
    /**
     * SS-SC-7 (CM_59)
     */
    @ApiOperation(value = "Delete the book from shelves")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted book"),
            @ApiResponse(code = 401, message = "You are not authorized to delete the resource"),
            @ApiResponse(code = 403, message = "The action is forbidden.")
    }
    )
    @DeleteMapping("/delete/{shelfid}/{bookid}")
    public void deleteBook(@PathVariable("bookid") String bookId,
                           @PathVariable("shelfid")  String shelfID,OAuth2Authentication auth) throws ShelfNotFound, NotFoundException {
        Shelf shelf = shelfService.getById(Long.valueOf(shelfID));
        if(!shelf.getUsername().equals(auth.getName())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"The action is forbidden.");
        }

        bookService.delete(bookId,shelfID);


    }
    /**
     * SS-SC-8 (CM_60)
     */
    @ApiOperation(value = "List tags")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved tags"),
            @ApiResponse(code = 401, message = "You are not authorized to get the resource")
    }
    )
    @GetMapping("/tags")
    public List<Tag> listTags(){
        return tagService.allTag();
    }

}
