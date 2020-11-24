package com.bookend.shelfservice.kafka;

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
    public void consumeBook(String message) {
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
            Tag checkTag = tagService.findByID(newGenre.getId());
            if(checkTag!=null){
                checkTag.setTag(newGenre.getGenre());
                tagService.save(checkTag);
            }
            else {
                tagService.save(new Tag(newGenre.getId(),newGenre.getGenre()));
            }


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }



}
