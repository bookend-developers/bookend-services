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
  1- Message Service: line 39 == -> != on findMessageByReceiver method of MessageServiceImp. <br />
  2- Mail Service: line 49-50 are deleted on sendConfirmationMailRequestsMail method of EmailMailSender <br />
  3- Book Club Service: line 175 null -> new Club() on savePost method of ClubService.

## Unit tests 
  1- failToGetMessageByReceiverIfReceiverDoesNotMatchAnyReceiver(Expected MessageNotFound to be thrown, but nothing was thrown.) and shouldGetMessagesByReceiverIfReceiverMatchAnyReceiver(MessageNotFound: Message does not exist.) tests are failed in MessageServiceTest. <br/>
  2- No tests are failed.
  3- No tests are failed.
 
 ## Integration Tests
    Shirley Persona fails at 'view incoming messages' request with message says  AssertionError: expected response to have status code 200 but got 500