package com.bookend.authorservice.config;

import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.repository.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = AuthorRepository.class)
@Configuration
public class MongoDBConfig {



}