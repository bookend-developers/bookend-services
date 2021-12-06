FROM maven:3.8.1-jdk-11 AS build

MAINTAINER ekrem

COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app  

RUN mvn -f /usr/src/app/pom.xml clean package

FROM gcr.io/distroless/java  
COPY --from=build /usr/src/app/target/book-club-service-0.0.1.jar /usr/app/book-club-service-0.0.1.jar 

ENTRYPOINT ["java","-jar","/usr/app/book-club-service-0.0.1.jar"]  

