package com.bookend.bookservice.kafka;

import com.bookend.bookservice.exception.NotFoundException;
import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.service.BookService;
import com.bookend.bookservice.service.SortService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class Listener {
    private SortService sortService;

    private BookService bookService;

    @Autowired
    public void setSortService(SortService sortService) {
        this.sortService = sortService;
    }


    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
    @KafkaListener(topics = "new-comment",
            groupId ="bookend-bookservice")
    public void newComment(String message){

        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String,String> msg = mapper.readValue(message,Map.class);
            Book book = bookService.getById(msg.get("book"));
            sortService.remove(book);
            System.out.println(msg.get("comment"));
            System.out.println(Long.valueOf(msg.get("comment")));
            book.getComments().add(Long.valueOf(msg.get("comment")));
            Book updatedBook = bookService.update(book);
            sortService.add(updatedBook);
            sortService.sort("comment");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

    }
    @KafkaListener(topics = "new-rate",
            groupId ="bookend-bookservice")
    public void newRate(String message){

        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String,String> msg = mapper.readValue(message,Map.class);

            Book book = bookService.getById(msg.get("book"));
            sortService.remove(book);
            book.setRate(Double.valueOf(msg.get("rate")));
            Book updatedBook = bookService.update(book);
            sortService.add(updatedBook);
            sortService.sort("rate");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
}
