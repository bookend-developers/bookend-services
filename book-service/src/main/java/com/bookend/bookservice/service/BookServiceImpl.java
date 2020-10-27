package com.bookend.bookservice.service;

import com.bookend.bookservice.kafka.Producer;
import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Book> getBooksofShelf(Long shelfID, String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+accessToken);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<String[]> responseEntity =restTemplate.exchange("http://localhost:8083/shelf/{shelfid}", HttpMethod.GET, entity, String[].class,shelfID);
        List<String> bookIDs = Arrays.asList(responseEntity.getBody());
        List<Book> books=new ArrayList<>(bookIDs.size());
        for(int i=0;i<bookIDs.size();i++){
            Book book = bookRepository.findBookById(bookIDs.get(i));
            books.add(book);
        }
        return books;
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> search(String title) {
        return bookRepository.findByBookNameContainingIgnoreCase(title);
    }

    @Override
    public void delete(String bookId) {
        bookRepository.delete(getById(bookId));
    }
}
