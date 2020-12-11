package com.bookend.authorservice.kafka;
import com.bookend.authorservice.kafka.Producer;
import com.bookend.authorservice.model.KafkaMessage;

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
import java.util.HashMap;
import java.util.Map;

@Component
public class Listener {
    private BookService bookService;
    private AuthorService authorService;
    private Producer producer;
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
          Author author = authorService.getById(msg.get("author"));

          Book book= new Book(msg.get("book"),author);
          bookService.save(book);
          author.getBookList().add(book);
          authorService.update(author);
          
          //To display kafka events!!!!! .... author's .. book has been succesfully saved to author service
         /* Map<String, String> message2= new HashMap<String, String>();
          message2.put("author",author.getName());
          message2.put("bookId",book.getBookId());
          message2.put("service", "author-service");
          KafkaMessage kafkaMessage = new KafkaMessage(KAFKA_TOPIC,message); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
          producer.bookPublished(kafkaMessage);*/
          
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @KafkaListener(topics = "deleting-book",
            groupId ="bookend-authorservice")
    public void deleteBook(String message){

        System.out.println(message);
        String[] splited = message.split("\"");
        bookService.deleteByBookId(splited[1]);
    }


}
