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
    public Book adminBook(@RequestBody BookRequest bookRequest){
        Book newBook= new Book();
        if(bookRequest.getBookName()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Book name field cannot be empty.");
        }
        newBook.setBookName(bookRequest.getBookName());
        if(bookRequest.getAuthor()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Author field cannot be empty.");
        }
        newBook.setAuthor(bookRequest.getAuthor());
        newBook.setDescription(bookRequest.getDescription());
        Genre genre = genreService.findByGenre(bookRequest.getGenre());
        if(genre == null){
            genre = genreService.addNewGenre(bookRequest.getGenre());
        }
        newBook.setGenre(genre);
        newBook.setAuthorid(bookRequest.getAuthorid());
        newBook.setPage(bookRequest.getPage());
        newBook.setVerified(Boolean.TRUE);

        return bookService.saveOrUpdate(newBook);
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

    }
    @ApiOperation(value = "Add new genre", response = Genre.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added genre"),
            @ApiResponse(code = 401, message = "You are not authorized to add the resource"),
            @ApiResponse(code = 400, message = "Resource already exists.")
    }
    )
    @PostMapping("/genre")
    public Genre addGenre(@RequestParam String genre){
        if(genreService.findByGenre(genre)==null){
            return genreService.addNewGenre(genre);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Genre already exists.");
        }
    }
    @GetMapping("/unverified")
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
        return bookService.saveOrUpdate(book);

    }

}
