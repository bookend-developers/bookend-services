package com.bookend.shelfservice.kafka;

import com.bookend.shelfservice.service.BookService;
import com.bookend.shelfservice.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    private ShelfService shelfService;
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
            groupId ="delete-book")
    public void consumeBook(String message) {
        System.out.println(message);
        String[] splited = message.split("\"");
        bookService.deleteFromShelves(splited[1]);


    }

    /*
    private ShelfService shelfService;
    @Autowired
    public void setShelfService(ShelfService shelfService){
        this.shelfService=shelfService;
    }
    private BookService bookService;
    @Autowired
    public void setBookService(BookService bookService){
        this.bookService=bookService;
    }
    @KafkaListener(topics = "add-book",
            groupId ="add-book-to-shelf")
    public void consumeBook(String message) {
        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        try {

            Book book = mapper.readValue(message, Book.class);
            bookService.saveOrUpdate(book);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
  */
}
