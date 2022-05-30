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

## Mutation
1- Authorization Service: added 'username = null' at line 100 findByUsername method at UserService.
2- Author Service: line 29 || changed as && on save method of BookServiceImpl.
3- Shelf Service: line 69 doubled deleteFromShelves method at BookServiceImpl.

## Unit Tests
1 - at com.bookend.authorizationserver.service.UserService.findByUsername test failed as "Strict stubbing argument mismatch".
2 - at com.bookend.authorizationserver.service.UserService.findByUsername test failed as "Strict stubbing argument mismatch".
3 - At com.bookend.authorservice.serviceTest.BookServiceTest.shouldFailSaveBookIfIDIsEmpty. AssertionFailedError: Expected com.bookend.authorservice.exception.MandatoryFieldException to be thrown
4 - at com.bookend.shelfservice.serviceTest.BookServiceTest.shouldDeleteBookFromAllShelves test failed as org.mockito.exceptions.verification.TooManyActualInvocations: 
## Integration Tests
  All pass