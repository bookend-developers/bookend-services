package com.mailservice.mailservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailservice.mailservice.payload.MailRequest;
import com.mailservice.mailservice.service.EmailMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MailListener {

    @Autowired
    EmailMailSender emailMailSender;

    @KafkaListener(topics = "Mail",
            groupId ="add-book-to-shelf")
    public void consumeBook(String message) {
        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            MailRequest mailRequest = mapper.readValue(message, MailRequest.class);
            emailMailSender.sendMailRequestsMail(mailRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
