package com.bookend.bookservice.controller;

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
        if(bookRequest.getBookName()==null  || bookRequest.getBookName().equals("")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Book name field cannot be empty.");
        }

        if(bookRequest.getAuthor()==null || bookRequest.getAuthor().equals("")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Author field cannot be empty.");
        }

        bookRequest.setVerified(true);
        Book addedBook =bookService.save(bookRequest);
        if(addedBook==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Book already exists.");
        }
        return addedBook;
    }
    @ApiOperation(value = "Delete the book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted book"),
            @ApiResponse(code = 401, message = "You are not authorized to delete the resource")
    }
    )
    @DeleteMapping("/{bookid}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable("bookid") String bookId){
        bookService.delete(bookId);

    }
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
        if(genre.equals("")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Genre must be filled.");
        }
        if(genreService.findByGenre(genre)==null){
            return genreService.addNewGenre(genre);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Genre already exists.");
        }
    }
    @GetMapping("/unverified")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Book> listUnverified(){
        return bookService.findBookByVerifiedIsFalse();
    }
    @PostMapping("/verify/{bookid}")
    public Book verifyBook(@PathVariable("bookid") String bookId){
        Book book = bookService.getById(bookId);
        if(book==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The book does not exist.");
        }
        book.setVerified(Boolean.TRUE);
        return bookService.update(book);

    }
    @GetMapping("/genres")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Genre> listGenres(){
        return genreService.findAll();
    }
    @PostMapping("/genre")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Genre updateGenre(@RequestBody Genre genre){
        Genre updatedGenre = genreService.findById(genre.getId());
        if(genreService.findByGenre(genre.getGenre())!=null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Genre already exist.");
        }
        if(updatedGenre!=null){
           return genreService.update(genre);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant found genre.");
        }
    }
}
