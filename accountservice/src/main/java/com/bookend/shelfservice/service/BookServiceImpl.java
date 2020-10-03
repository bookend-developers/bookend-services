package com.bookend.shelfservice.service;

import com.bookend.shelfservice.model.Book;
import com.bookend.shelfservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    @Autowired
    public void setBookRepository(BookRepository bookRepository){
        this.bookRepository=bookRepository;
    }
    @Override
    public Book getById(String id) {
        return bookRepository.findBookById(id);
    }


    @Override
    public Book saveOrUpdate(Book book) {
        return bookRepository.save(book);
    }
}
