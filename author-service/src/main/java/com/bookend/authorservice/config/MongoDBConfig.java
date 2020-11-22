package com.bookend.authorservice.config;

import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.repository.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@EnableMongoRepositories(basePackageClasses = AuthorRepository.class)
@Configuration
public class MongoDBConfig {
 //  @Bean
 //  CommandLineRunner commandLineRunner(AuthorRepository authorRepository){
 //      authorRepository.deleteAll();
 //      return strings -> {
 //          authorRepository.save(new Author("Ivan Goncharov",
 //                  "Ivan Alexandrovich Goncharov (Russian: Иван Александрович Гончаров) was a Russian novelist best known as the author of Oblomov (1859).",
 //                  LocalDate.parse("18-Jun-1812", DateTimeFormatter.ofPattern("d-MMM-yyyy", Locale.US)),
 //                  LocalDate.parse("27-Sep-1891", DateTimeFormatter.ofPattern("d-MMM-yyyy", Locale.US))));
 //          authorRepository.save(new Author("Ray Bradbury",
 //                  "Ray Douglas Bradbury, American novelist, short story writer, essayist, playwright, screenwriter and poet, was born August 22, 1920 in Waukegan, Illinois. He graduated from a Los Angeles high school in 1938. Although his formal education ended there, he became a \"student of life,\" selling newspapers on L.A. street corners from 1938 to 1942, spending his nights in the public library and his days at the typewriter. He became a full-time writer in 1943, and contributed numerous short stories to periodicals before publishing a collection of them, Dark Carnival, in 1947.",
 //                  LocalDate.parse("22-Aug-1920", DateTimeFormatter.ofPattern("d-MMM-yyyy", Locale.US)),
 //                  LocalDate.parse("5-Jun-2012", DateTimeFormatter.ofPattern("d-MMM-yyyy", Locale.US))));
 //
 //      };
 //  }



}