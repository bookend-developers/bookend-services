package com.ratecommentservice.service;

import com.ratecommentservice.model.Book;
import com.ratecommentservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book findBookByBookID(String bookid) {
        return bookRepository.findBookByBookId(bookid);
    }
}
