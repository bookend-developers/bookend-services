package com.bookclupservice.bookclubservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
public class BookClubServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookClubServiceApplication.class, args);
	}

}
