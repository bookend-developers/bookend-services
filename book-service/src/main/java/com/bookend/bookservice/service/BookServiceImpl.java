package com.bookend.bookservice.service;

import com.bookend.bookservice.kafka.Producer;
import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private static final String BOOK_TOPIC = "add-book";
    private static final String SHELF_TOPIC = "to-shelf";

    private BookRepository bookRepository;
    @Autowired
    public void setBookRepository(BookRepository bookRepository){
        this.bookRepository=bookRepository;
    }

    private Producer producer;
    @Autowired
    public void setProducer(Producer producer){this.producer=producer;}
    @Override
    public Book getById(String id) {
        return bookRepository.findBookById(id);
    }



    @Override
    public Book saveOrUpdate(Book book) {
        bookRepository.save(book);
        return book;
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }
}
