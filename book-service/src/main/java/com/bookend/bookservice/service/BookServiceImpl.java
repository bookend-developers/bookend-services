package com.bookend.bookservice.service;

import com.bookend.bookservice.kafka.Producer;
import com.bookend.bookservice.model.Author;
import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.model.KafkaMessage;
import com.bookend.bookservice.repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private static final String BOOK_TOPIC = "adding-book";
    private static final String DELETE_TOPIC = "deleting-book";

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
    public Map<String,String> getFullBookById(String id, String accessToken) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> fullBook = new HashMap<>();
        String bookstr = new String();
        String authorstr = new String();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+accessToken);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        Book book = bookRepository.findBookById(id);
        if(book==null){
          return null;
        }
        ResponseEntity<Author> responseEntity =restTemplate.exchange("http://localhost:8085/api/author/{authorid}", HttpMethod.GET, entity, Author.class,book.getAuthor());
        Author author= responseEntity.getBody();

        try {
             bookstr = mapper.writeValueAsString(book);
             authorstr = mapper.writeValueAsString(author);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        fullBook.put("book",bookstr);
        fullBook.put("author", authorstr);





        return fullBook;
    }



    @Override
    public Book saveOrUpdate(Book book) {
        Map<String, String> message= new HashMap<String, String>();
        Book savedBook = bookRepository.save(book);
        message.put("author",book.getAuthor());
        message.put("book",savedBook.getId());
        KafkaMessage kafkaMessage = new KafkaMessage(BOOK_TOPIC,message);
        producer.publishBook(kafkaMessage);
        return savedBook;
    }

    @Override
    public List<Book> getBooksofShelf(Long shelfID, String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+accessToken);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<String[]> responseEntity =restTemplate.exchange("http://localhost:8083/api/shelf/{shelfid}", HttpMethod.GET, entity, String[].class,shelfID);
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
    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public List<Book> search(String title) {
        return bookRepository.findByBookNameContainingIgnoreCase(title);
    }

    @Override
    public void delete(String bookId) {
        KafkaMessage kafkaMessage = new KafkaMessage(DELETE_TOPIC,bookId);
        producer.deleteBook(kafkaMessage);
        bookRepository.delete(getById(bookId));
    }
}
