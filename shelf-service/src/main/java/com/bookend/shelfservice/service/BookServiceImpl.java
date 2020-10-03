package com.bookend.shelfservice.service;

import com.bookend.shelfservice.model.ShelfsBook;
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
    public ShelfsBook getById(String id) {
        return bookRepository.findBookById(id);
    }


    @Override
    public ShelfsBook saveOrUpdate(ShelfsBook shelfsBook) {
        return bookRepository.save(shelfsBook);
    }
}
