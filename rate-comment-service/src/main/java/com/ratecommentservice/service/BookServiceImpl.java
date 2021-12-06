package com.ratecommentservice.service;

import com.ratecommentservice.exception.BookNotFound;
import com.ratecommentservice.model.Book;
import com.ratecommentservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public Book findBookByBookID(String bookid) throws BookNotFound {
        Book book = bookRepository.findBookByBookId(bookid);
        if(book == null){
            throw new BookNotFound("Book is not found..");
        }

        return book;
    }

    @Override
    public List<String> findAll() {
        List<Book> books=bookRepository.findAll().stream()
                .sorted(Comparator.comparingDouble(Book::getAverageRate).reversed())
                .collect(Collectors.toList());

        List<String> bookids = books.stream().map(book -> book.getBookid()).collect(Collectors.toList());

        return bookids ;
    }

}
