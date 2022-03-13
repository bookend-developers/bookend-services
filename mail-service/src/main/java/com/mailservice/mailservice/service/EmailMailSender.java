package com.mailservice.mailservice.service;

import com.mailservice.mailservice.model.User;
import com.mailservice.mailservice.payload.MailRequest;
import com.mailservice.mailservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
/**
 * MAILS-MSC stands for MailService-MailSenderClass
 * SM stands for ServiceMethod
 */
@Service
public class EmailMailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    /**
     * MAILS-MSC-1 (SM_49)
     */
    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    /**
     * MAILS-MSC-2 (SM_50)
     */
    public void sendMailRequestsMail(MailRequest mailRequest){
        User user = userRepository.findById(mailRequest.getId()).get();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("gradd.sschool@gmail.com");
        mailMessage.setSubject(mailRequest.getSubject());
        mailMessage.setText(mailRequest.getText());
        sendEmail(mailMessage);
    }
    /**
     * MAILS-MSC-3 (SM_51)
     */
    public void sendConfirmationMailRequestsMail(MailRequest mailRequest){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailRequest.getEmail());
        mailMessage.setFrom("gradd.sschool@gmail.com");
        mailMessage.setSubject(mailRequest.getSubject());
        mailMessage.setText(mailRequest.getText());
        sendEmail(mailMessage);
    }
    /**
     * MAILS-MSC-4 (SM_52)
     */
    public void sendResetPasswordMailRequestsMail(MailRequest mailRequest){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailRequest.getEmail());
        mailMessage.setFrom("gradd.sschool@gmail.com");
        mailMessage.setSubject(mailRequest.getSubject());
        mailMessage.setText(mailRequest.getText());
        sendEmail(mailMessage);
    }

}
