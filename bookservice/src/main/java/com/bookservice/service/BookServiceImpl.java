package com.bookservice.service;

import com.bookservice.kafka.Producer;
import com.bookservice.model.Book;

import com.bookservice.model.KafkaMessage;
import com.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Book getById(Long id) {
        return bookRepository.findBookById(id);
    }



    @Override
    public Book saveOrUpdate(Book book) {
        bookRepository.save(book);
        return book;
    }
}
