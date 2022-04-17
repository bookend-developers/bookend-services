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
Book Club Service: line 105 == -> != on newMember() method of ClubService.<br />
Rate Comment Service: line 61 == -> != on save() method of RateServiceImpl.<br />

## Unit test results
1st mutation: <br />
- com.bookend.bookclubservice.serviceTest.clubServiceTest.shouldSaveNewMemberToClub -> Expected :true, Actual :false <br />
- com.bookend.bookclubservice.serviceTest.clubServiceTest.shouldNotSaveNewMemberToClubIfUserIsOwnerOfClub -> org.mockito.exceptions.misusing.UnnecessaryStubbingException <br />
- com.bookend.bookclubservice.serviceTest.clubServiceTest.shouldNotSaveNewMemberToClubIfUserAllreadMemberOfClub -> org.mockito.exceptions.misusing.UnnecessaryStubbingException <br />
- com.bookend.bookclubservice.serviceTest.clubServiceTest.shouldNotSaveNewMemberToClubIfUserNotExists -> java.util.NoSuchElementException: No value present <br /><br />
2nd mutation: <br />
- com.ratecommentservice.serviceTest.RateServiceTest.shouldSaveNonExistingRateWhenBookDoesNotExist -> java.lang.NullPointerException<br />
- com.ratecommentservice.serviceTest.RateServiceTest.shouldSaveGivenRateSuccessfully -> org.mockito.exceptions.misusing.PotentialStubbingProblem<br />

## Integration tests
Pierce persona delete comment request fails as expected response to have status code 200 but got 500