# bookend-services
## Bookend
  Our project is a social website for book lovers. Its mission is to help people find and share
  books they love. It is a platform that allows users to rate and comment about books. Users can also get
  information about books and authors by filtering, create virtual shelves to add books as their wishes
  and join clubs to make a discussion about their interests and communicate. In addition to those, users 
  can message each other outside of clubs. Thanks to it, they can create a book-based social network for
  themselves. 
## Architecture
![Ekran görüntüsü 2021-02-14 150218](https://user-images.githubusercontent.com/37040918/107876215-b1805880-6ed5-11eb-80d9-ccd244238eb7.png)

## Technologies
  Project’s backend services will be implemented with Spring Boot, an open-source Java-based framework.<br />
  Each service will connect to different database servers. In these services, two types of DB will be used. MongoDB, which is a NoSQL database, will be used. MySQL will also be used for the purpose of variation. <br />
  Kafka will be used for asynchronous communication between services so that our microservice application will be more loosely-coupled. <br />
  React ,an efficient, flexible JavaScript library that allows us to create complex interface units will be used for frontend service, and Material-UI framework will be benefited for faster and easier development. <br />
  Postman tool will be used for unit and integration testing, and in addition to Postman, JUnit will be used for unit testing.<br />

## Mutations
  Book service -> BookServiceImpl line 50
  before :  Book book = bookRepository.findBookById(id);
  after :   Book book = bookRepository.findBookById(null);

  Message service -> MessageServiceImpl line 96
  before :   List<Message> chat = new ArrayList<Message>();
	after : 	 List<Message> chat = null;
  
## Unit Tests
  failed:
  com.bookend.bookservice.serviceTest.BookServiceTest#shouldDeleteBookWithGivenId
  com.bookend.bookservice.serviceTest.BookServiceTest#verifyBook
  com.bookend.bookservice.serviceTest.BookServiceTest#shouldFailToReturnIfBookDoesNotExistWithGivenId
  com.bookend.bookservice.serviceTest.BookServiceTest#failToVerifyWhenIDHaveNotMatch
  com.bookend.bookservice.serviceTest.BookServiceTest#shouldFailToDeleteBookIfNoBookExistsWithGivenId
  com.bookend.bookservice.serviceTest.BookServiceTest#shouldReturnBookWithGivenId
  com.bookend.messageservice.serviceTest.MessageServiceTest#findChatByUserName
