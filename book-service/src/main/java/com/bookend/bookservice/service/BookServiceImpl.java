package com.bookend.bookservice.service;

import com.bookend.bookservice.kafka.Producer;
import com.bookend.bookservice.model.*;
import com.bookend.bookservice.payload.BookRequest;
import com.bookend.bookservice.repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    private GenreService genreService;
    private SortService sortService;



    @Autowired
    public void setSortService(SortService sortService) {
        this.sortService = sortService;
    }

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

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
    public Book save(BookRequest bookRequest) {
        List<Book> books = bookRepository.findBookByBookName(bookRequest.getBookName());
        if(books.size()!=0){
            List<Book> filteredbyAuthor = books.stream()
                    .filter(b -> b.getAuthorid().equals(bookRequest.getAuthorid()))
                    .collect(Collectors.toList());
            if(filteredbyAuthor.size()!=0){
                List<Book> filteredbyDesc = filteredbyAuthor.stream()
                        .filter(b -> b.getDescription().equals(bookRequest.getDescription()))
                        .collect(Collectors.toList());
                if(filteredbyDesc.size()!=0){
                    return null;
                }
            }
        }

        Book book = new Book();
        book.setBookName(bookRequest.getBookName());
        book.setAuthor(bookRequest.getAuthor());
        book.setAuthorid(bookRequest.getAuthorid());
        book.setDescription(bookRequest.getDescription());
        book.setPage(bookRequest.getPage());
        book.setVerified(bookRequest.getVerified());
        book.setISBN(bookRequest.getISBN());
        Genre genre = genreService.findByGenre(bookRequest.getGenre());
        if(genre == null){
            genre = genreService.addNewGenre(bookRequest.getGenre());
        }
        book.setGenre(genre);

        Book savedBook = bookRepository.save(book);
        sortService.add(book);

        Map<String, String> message= new HashMap<String, String>();
        message.put("author",savedBook.getAuthorid());
        message.put("bookRequest",savedBook.getId());
        KafkaMessage kafkaMessage = new KafkaMessage(BOOK_TOPIC,message);
        producer.publishBook(kafkaMessage);
        return savedBook;
    }

    @Override
    public Book update(Book book) {
        SortedLists sortedLists = sortService.findOne();
        sortedLists.getSortedByRate().remove(book);
        return bookRepository.save(book);
    }



    @Override
    public List<Book> getAll() {
        return bookRepository.findAll(Sort.by(Sort.Direction.ASC,"bookName"));
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthorid(author);
    }

    @Override
    public List<Book> search(String title,String genre, boolean rateSort,boolean commentSort,String accessToken) {
        List<Book> books = new ArrayList<>();
        if(rateSort){
           books = sortService.findOne().getSortedByRate();
           Collections.reverse(books);
        }
        else if(commentSort){
            books = sortService.findOne().getSortedByComment();
            Collections.reverse(books);
        }
        else {
            books = getAll();
        }

        if(title!=null){
            books = books.stream()
                    .filter(book -> book.getBookName().toLowerCase()
                            .contains(title.toLowerCase()))
                    .collect(Collectors.toList());
            if(books.size()==0){
                return null;
            }
        }
        if(genre!=null){
            books = books.stream()
                    .filter(book -> book.getGenre().getGenre().toLowerCase()
                            .contains(genre.toLowerCase()))
                    .collect(Collectors.toList());
            if(books.size()==0){
                return null;
            }
        }

        return books;
    }

    @Override
    public void delete(String bookId) {
        KafkaMessage kafkaMessage = new KafkaMessage(DELETE_TOPIC,bookId);
        producer.deleteBook(kafkaMessage);
        bookRepository.delete(getById(bookId));
    }

    @Override
    public List<Book> findBookByVerifiedIsFalse() {
        return bookRepository.findBookByVerifiedIsFalse();
    }
}
