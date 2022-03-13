package com.bookend.bookservice.controller;

import com.bookend.bookservice.exception.AlreadyExist;
import com.bookend.bookservice.exception.MandatoryFieldException;
import com.bookend.bookservice.exception.NotFoundException;
import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.model.Genre;
import com.bookend.bookservice.payload.BookRequest;
import com.bookend.bookservice.service.BookService;
import com.bookend.bookservice.service.GenreService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
/**
 * BS-AC stands for BookService-AdminController
 * CM stands for ControllerMethod
 */
@RestController
@RequestMapping("/api/book/admin")
public class AdminController {
    private BookService bookService;
    private GenreService genreService;
    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    @Autowired
    public void setBookService(BookService bookService){
        this.bookService=bookService;
    }
    /**
     * BS-AC-1 (CM_25)
     */
    @ApiOperation(value = "Add new book", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added book"),
            @ApiResponse(code = 401, message = "You are not authorized to add the resource"),
            @ApiResponse(code = 400, message = "The way you are trying to add book is not accepted.")
    }
    )
    @PostMapping("/new")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Book adminBook(@RequestBody BookRequest bookRequest){
        bookRequest.setVerified(true);
        try {
            return bookService.save(bookRequest);
        } catch (MandatoryFieldException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        } catch (AlreadyExist alreadyExist) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,alreadyExist.getMessage());
        }


    }
    /**
     * BS-AC-2 (CM_26)
     */
    @ApiOperation(value = "Delete the book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted book"),
            @ApiResponse(code = 401, message = "You are not authorized to delete the resource")
    }
    )
    @DeleteMapping("/{bookid}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable("bookid") String bookId){
        try {
            bookService.delete(bookId);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }

    }
    /**
     * BS-AC-3 (CM_27)
     */
    @ApiOperation(value = "Add new genre", response = Genre.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added genre"),
            @ApiResponse(code = 401, message = "You are not authorized to add the resource"),
            @ApiResponse(code = 400, message = "Resource already exists.")
    }
    )
    @PostMapping("/new/genre")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Genre addGenre(@RequestParam String genre){

        try {
            return genreService.addNewGenre(genre);
        } catch (AlreadyExist alreadyExist) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,alreadyExist.getMessage());
        } catch (MandatoryFieldException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }


    }
    /**
     * BS-AC-4 (CM_28)
     */
    @GetMapping("/unverified")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Book> listUnverified(){
        try {
            return bookService.findBookByVerifiedIsFalse();
        } catch (NotFoundException e) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
    /**
     * BS-AC-5 (CM_29)
     */
    @PostMapping("/verify/{bookid}")
    public Book verifyBook(@PathVariable("bookid") String bookId){
        try {
            return bookService.verify(bookId);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }

    }
    /**
     * BS-AC-6 (CM_30)
     */
    @GetMapping("/genres")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Genre> listGenres(){
        return genreService.findAll();
    }
    /**
     * BS-AC-7 (CM_31)
     */
    @PostMapping("/genre")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Genre updateGenre(@RequestBody Genre genre){
        try {
            return genreService.update(genre);
        } catch (MandatoryFieldException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        } catch (AlreadyExist alreadyExist) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,alreadyExist.getMessage());
        }
    }
}
