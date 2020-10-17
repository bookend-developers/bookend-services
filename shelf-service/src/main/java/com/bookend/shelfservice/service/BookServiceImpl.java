package com.bookend.shelfservice.service;

import com.bookend.shelfservice.model.Shelf;
import com.bookend.shelfservice.model.ShelfsBook;
import com.bookend.shelfservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<ShelfsBook> books = bookRepository.findShelfsBookByShelf(shelfsBook.getShelf());
        if(books.stream().anyMatch(book -> book.getBookID().matches(shelfsBook.getBookID()))){
            return null;
        }
        return bookRepository.save(shelfsBook);
    }
}
//5f7a1001caa59177cb3cd96e