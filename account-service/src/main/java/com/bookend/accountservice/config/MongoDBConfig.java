package com.bookend.accountservice.config;

import com.bookend.accountservice.model.Account;
import com.bookend.accountservice.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = AccountRepository.class)
@Configuration
public class MongoDBConfig {
    @Bean
    CommandLineRunner commandLineRunner(AccountRepository accountRepository) {
        accountRepository.deleteAll();
        return strings -> {

                accountRepository.save(new Account( "firstname",  "lastname",  "krish",  "k@krishantha.com"));
        };
    }

}
