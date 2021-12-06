package com.mailservice.mailservice.serviceTest;

import com.mailservice.mailservice.model.User;
import com.mailservice.mailservice.payload.MailRequest;
import com.mailservice.mailservice.repository.UserRepository;
import com.mailservice.mailservice.service.EmailMailSender;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class EMailMailSenderServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private EmailMailSender emailMailSender;
    @Mock
    private JavaMailSender javaMailSender;
    MailRequest mailRequest;
    User user;
    public EMailMailSenderServiceTest() {
    }

    @BeforeEach
    void setUp(){
        mailRequest = new MailRequest();
        final Long id = Long.valueOf(1213);
        user = new User();
        final String to = "test@mail.com";
        user.setEmail(to);
        user.setId(id);
        mailRequest.setId(id);
        mailRequest.setSubject("SubjectTest");
        mailRequest.setText("TestText....");
    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    public void sendSuccessfullyRequestMail()  {

        when(userRepository.findById(mailRequest.getId())).thenReturn(Optional.of(user));
        emailMailSender.sendMailRequestsMail(mailRequest);
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    public void sendSuccessfullyConfirmationMailRequestsMail()  {

        emailMailSender.sendConfirmationMailRequestsMail(mailRequest);
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    public void sendSuccessfullyResetPasswordMailRequestsMail()  {
        emailMailSender.sendResetPasswordMailRequestsMail(mailRequest);
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    public void sendSuccessfullyMail()  {
        final  SimpleMailMessage mailMessage = new SimpleMailMessage();
        emailMailSender.sendEmail(mailMessage);
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
