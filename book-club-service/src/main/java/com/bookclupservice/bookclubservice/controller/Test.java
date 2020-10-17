package com.bookclupservice.bookclubservice.controller;

import com.bookclupservice.bookclubservice.kafka.MessageProducer;
import com.bookclupservice.bookclubservice.payload.MailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @Autowired
    MessageProducer messageProducer;

    @GetMapping("/test")
    public String testGet(){

        return "sdfdsgds";
    }

    @GetMapping("/test-kafka")
    public void testkafka(){
        MailRequest mailRequest = new MailRequest((long)1,"Test Request","Test Text");

        messageProducer.sendMailRequest(mailRequest);
    }
}
