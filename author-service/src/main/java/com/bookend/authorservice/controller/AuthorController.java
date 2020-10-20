package com.bookend.authorservice.controller;

import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.model.Book;
import com.bookend.authorservice.payload.AuthorRequest;
import com.bookend.authorservice.service.AuthorService;
import com.bookend.authorservice.service.BookService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

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

    @GetMapping("/{authorid}")
    public Author getAuthorInfo(@PathVariable("authorid") String authorId) {
        return authorService.getById(authorId);

    }
    @GetMapping("")
    public List<Author> getAllAuthor(@RequestParam(required = false) String title){
        List<Author> authors = new ArrayList<Author>();
        if(title== null){
            authorService.getAll().forEach(authors::add);
        }
        else{
            authorService.search(title).forEach(authors::add);
        }

        return authors;
    }
    @PostMapping("/admin/new")
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
    @PostMapping("/admin/newbook")
    public Book newBook(@RequestParam String bookid,
                        @RequestParam String authorid){
        Author author = authorService.getById(authorid);
        if(author== null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The author does not found.");
        }

        Book book = new Book(bookid,author);
        Book savedBook = bookService.save(book);
        author.getBookList().add(book);


        authorService.saveOrUpdate(author);
        return savedBook;

    }
    @GetMapping("/books")
    public List<Book> getAuthorBooks(@RequestParam String authorid){
        Author author = authorService.getById(authorid);
        if(author== null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The author does not found.");
        }
        return author.getBookList();
    }
}
