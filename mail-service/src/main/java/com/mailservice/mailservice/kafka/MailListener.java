package com.mailservice.mailservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailservice.mailservice.payload.KafkaUserRegistered;
import com.mailservice.mailservice.payload.MailRequest;
import com.mailservice.mailservice.service.EmailMailSender;
import com.mailservice.mailservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MailListener {

    @Autowired
    EmailMailSender emailMailSender;
    @Autowired
    UserService userService;

    @KafkaListener(topics = "confirmation-mail",
            groupId ="mailservice")
    public void consumeConfirmMail(String message) {
        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            MailRequest mailRequest = mapper.readValue(message, MailRequest.class);
            emailMailSender.sendConfirmationMailRequestsMail(mailRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "Mail",
            groupId ="mailservice")
    public void consumeMail(String message) {
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

    @KafkaListener(topics = "user-registered",
            groupId ="mailservice")
    public void saveUser(String message) {
        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            KafkaUserRegistered kafkaUserRegistered = mapper.readValue(message, KafkaUserRegistered.class);
            userService.save(kafkaUserRegistered.getId(),kafkaUserRegistered.getMail());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    @KafkaListener(topics = "resetPassword-mail",
            groupId ="mailservice")
    public void consumeResetPasswordMail(String message) {
        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            MailRequest mailRequest = mapper.readValue(message, MailRequest.class);
            emailMailSender.sendResetPasswordMailRequestsMail(mailRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
