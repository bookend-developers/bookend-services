package com.mailservice.mailservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailMailSender {

    @Autowired
    private JavaMailSender javaMailSender;


    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    public void sendMailtest(String toEmail, String subject, String message) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setFrom("gradd.sschool@gmail.com");
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        javaMailSender.send(mailMessage);
    }


}
