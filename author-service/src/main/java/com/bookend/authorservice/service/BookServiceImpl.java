package com.bookend.authorservice.service;

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
    public Book findByBookid(String bookId) {
        return bookRepository.findByBookId(bookId);
    }

    @Override
    public Book save(Book book) {

        return bookRepository.save(book);
    }

    @Override
    public void deleteByBookId(String bookId) {
        bookRepository.deleteByBookId(bookId);
    }
}
