package com.bookend.shelfservice.kafka;

import com.bookend.shelfservice.exception.AlreadyExists;
import com.bookend.shelfservice.exception.NotFoundException;
import com.bookend.shelfservice.exception.TagNotFound;
import com.bookend.shelfservice.model.Tag;
import com.bookend.shelfservice.payload.GenreMessage;
import com.bookend.shelfservice.service.BookService;
import com.bookend.shelfservice.service.ShelfService;
import com.bookend.shelfservice.service.TagService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Listener {

    private ShelfService shelfService;
    private TagService tagService;



    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setShelfService(ShelfService shelfService){
        this.shelfService=shelfService;
    }
    private BookService bookService;
    @Autowired
    public void setBookService(BookService bookService){
        this.bookService=bookService;
    }
    @KafkaListener(topics = "deleting-book",
            groupId ="bookend-shelfservice")
    public void consumeBook(String message) throws NotFoundException {
        System.out.println(message);
        String[] splited = message.split("\"");
        bookService.deleteFromShelves(splited[1]);


    }
    @KafkaListener(topics = "adding-genre",
            groupId ="bookend-shelfservice")
    public void consumeGenre(String message) {
        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            GenreMessage newGenre = mapper.readValue(message, GenreMessage.class);
            tagService.save(newGenre);


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AlreadyExists alreadyExists) {
            alreadyExists.printStackTrace();
        }


    }



}
