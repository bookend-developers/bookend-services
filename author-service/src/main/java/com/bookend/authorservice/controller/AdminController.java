package com.bookend.authorservice.controller;

import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.model.Book;
import com.bookend.authorservice.payload.AuthorRequest;
import com.bookend.authorservice.service.AuthorService;
import com.bookend.authorservice.service.BookService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;

@RestController
@RequestMapping("/api/admin/author")
public class AdminController {
    private AuthorService authorService;
    private BookService bookService;
    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService){
        this.authorService=authorService;
    }
    @ApiOperation(value = "Add new author", response = Author.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added author"),
            @ApiResponse(code = 401, message = "You are not authorized to add the resource")
    }
    )
    @PostMapping("/new/author")
    public Author newAuthor(@RequestBody AuthorRequest author) throws ParseException {
        Author newAuthor = new Author();
        newAuthor.setName(author.getName());
        newAuthor.setBiography(author.getBiography());
        newAuthor.setBirthDate(author.getBirthDate());
        if(author.getDateOfDeath()==null){
            newAuthor.setDateOfDeath(null);
        }else{
            newAuthor.setDateOfDeath(author.getDateOfDeath());
        }

        return authorService.saveOrUpdate(newAuthor);

    }
    @ApiOperation(value = "Add new book to shelf", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added book"),
            @ApiResponse(code = 401, message = "You are not authorized to add the resource"),
            @ApiResponse(code = 400, message = "The book is already exists in author's shelf."),
            @ApiResponse(code = 404, message = "The author is not found.")
    }
    )
    @PostMapping("/new/book")
    public Book newBook(@RequestParam String bookid,
                        @RequestParam String authorid){
        Author author = authorService.getById(authorid);
        if(author== null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The author does not exist.");
        }

        Book book = new Book(bookid,author);
        Book savedBook = bookService.save(book);
        if(savedBook==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The book is already in author's shelf.");
        }
        author.getBookList().add(book);


        authorService.saveOrUpdate(author);
        return savedBook;

    }
   //TODO kafka listener will delete book info if the book service deletes book
}
