package com.bookclupservice.bookclubservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {


    @GetMapping("/test")
    public String testGet(){

        return "sdfdsgds";
    }
}
