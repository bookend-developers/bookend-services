package com.bookend.bookservice.config;

import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = BookRepository.class)
@Configuration
public class MongoDBConfig {


    @Bean
    CommandLineRunner commandLineRunner(BookRepository bookRepository) {
        bookRepository.deleteAll();
        return strings -> {
            bookRepository.save(new Book(45,"fiction","descrip", "cin ali","who"));
            bookRepository.save(new  Book(300,"drama","wow", "cin ali fil yıkıyor","anonim"));


        };
    }

}