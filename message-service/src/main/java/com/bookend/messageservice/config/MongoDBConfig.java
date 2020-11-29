package com.bookend.messageservice.config;

import com.bookend.messageservice.model.Message;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.bookend.messageservice.repository.MessageRepository;

import java.util.Date;


@EnableMongoRepositories(basePackageClasses = MessageRepository.class)
@Configuration
public class MongoDBConfig {
    //after running one time make the method as comment
    /*
    @Bean
    CommandLineRunner commandLineRunner(MessageRepository messageRepository) {
        messageRepository.deleteAll();
        return strings -> {
            messageRepository.save(new Message("krish","suranga","deneme","selamm",new Date()));

        };
    }*/

}
