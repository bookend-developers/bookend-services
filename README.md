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
  1- Book Service: line 73 == -> != on update() method of GenreServiceImpl. </br>
  2- Message Service: line 54-56 are deleted on saveOrUpdate() method of MessageServiceImpl. </br>
  3- Shelf Service: line 66 || -> && on deleteFromShelves() method of BookServiceImpl. </br>

## Unit Tests
  1- shouldUpdateGenre (MandatoryFieldException: Genre cannot be empty) and shouldFailToUpdateGenreIfGenreNAmeNull(Expected MandatoryFieldException to be thrown, but nothing was thrown) tests are failed. </br>
  2- failToSaveMessageIfSenderIsEmpty (Expected MandatoryFieldException to be thrown, but nothing was thrown.) test is failed. </br>
  3- failToDeleteBookFromAllShelvesIfBookDoesNotExistInShelves (Unexpected exception type thrown ==> expected: NotFoundException but was: NullPointerException) test is failed.
  