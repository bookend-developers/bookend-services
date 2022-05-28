package com.bookend.authorservice.service;

import com.bookend.authorservice.exception.MandatoryFieldException;
import com.bookend.authorservice.exception.NotFoundException;
import com.bookend.authorservice.model.Book;
import com.bookend.authorservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl  implements BookService{
     private BookRepository bookRepository;
    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book findByBookid(String bookId) throws NotFoundException {
        Book book = bookRepository.findByBookId(bookId);
        if(book == null){
            throw new NotFoundException("No book exist with given ID");
        }
        return book;
    }

    @Override
    public Book save(Book book) throws MandatoryFieldException {
        if(book.getBookId()==null && book.getBookId()==""){
            throw new MandatoryFieldException("Book id cannot be empty");
        }
        return bookRepository.save(book);
    }

    @Override
    public void deleteByBookId(String bookId) throws NotFoundException {
        findByBookid(bookId);
        bookRepository.deleteByBookId(bookId);
    }
}
