package com.bookend.authorservice.kafka;

import com.bookend.authorservice.exception.MandatoryFieldException;
import com.bookend.authorservice.exception.NotFoundException;
import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.model.Book;
import com.bookend.authorservice.service.AuthorService;
import com.bookend.authorservice.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class Listener {
    private BookService bookService;
    private AuthorService authorService;
    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }
    @KafkaListener(topics = "adding-book",
            groupId ="bookend-authorservice")
    public void consumeBook(String message) {
        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        try {
          Map<String,String> msg = mapper.readValue(message,Map.class);

          authorService.update(msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException notFoundException) {
            notFoundException.printStackTrace();
        } catch (MandatoryFieldException e) {
            e.printStackTrace();
        }

    }
    @KafkaListener(topics = "deleting-book",
            groupId ="bookend-authorservice")
    public void deleteBook(String message){

        System.out.println(message);
        String[] splited = message.split("\"");
        try {
            bookService.deleteByBookId(splited[1]);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }


}
