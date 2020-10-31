package com.mailservice.mailservice.service;

import com.mailservice.mailservice.model.User;
import com.mailservice.mailservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailMailSender emailMailSender;

    public User save(Long id,String email){
        User user = new User();
        user.setEmail(email);
        user.setId(id);
        return userRepository.save(user);
    }

    public void sendConfirmationMail(String toEmail, String token){
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("Mail Confirmation Link!");
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText(
                "Kayıt olduğunuz için teşekkürler.\nKullanıcı adınız: " + toEmail + "\n Aktivasyon linki: " +
                        "" + "localhost:9191/oauth/confirm"  + token +
                        "\n\n" +
                        "Thank you for registering.\nYour username: "+ toEmail+ "\n Activation link: " +
                        "" + "localhost:9191/oauth/confirm/"+ token);

        emailMailSender.sendEmail(mailMessage);
    }
}
