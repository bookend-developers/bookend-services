package com.mailservice.mailservice.service;

import com.mailservice.mailservice.model.User;
import com.mailservice.mailservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
/**
 * MAILS-USC stands for MailService-UserServiceClass
 * SM stands for ServiceMethod
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailMailSender emailMailSender;
    /**
     * MAILS-USC-1 (SM_53)
     */
    public User save(Long id,String email){
        if(id==null || email ==null)
            throw new IllegalArgumentException("email or id can not be null");
        User user = new User();
        user.setEmail(email);
        user.setId(id);
        return userRepository.save(user);
    }


}
