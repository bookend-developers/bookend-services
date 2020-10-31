package com.mailservice.mailservice.service;

import com.mailservice.mailservice.model.User;
import com.mailservice.mailservice.payload.MailRequest;
import com.mailservice.mailservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailMailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

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

    public void sendMailRequestsMail(MailRequest mailRequest){
        User user = userRepository.findById(mailRequest.getId()).get();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("gradd.sschool@gmail.com");
        mailMessage.setSubject(mailRequest.getSubject());
        mailMessage.setText(mailRequest.getText());
        sendEmail(mailMessage);
    }

    public void sendConfirmationMailRequestsMail(MailRequest mailRequest){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailRequest.getEmail());
        mailMessage.setFrom("gradd.sschool@gmail.com");
        mailMessage.setSubject(mailRequest.getSubject());
        mailMessage.setText(mailRequest.getText());
        sendEmail(mailMessage);
    }


}
