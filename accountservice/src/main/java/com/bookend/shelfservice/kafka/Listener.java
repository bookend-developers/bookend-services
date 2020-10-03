package com.bookend.shelfservice.kafka;

//@Component
public class Listener {
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
