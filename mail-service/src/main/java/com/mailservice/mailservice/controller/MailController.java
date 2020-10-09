package com.mailservice.mailservice.controller;

import com.mailservice.mailservice.service.EmailMailSender;
import com.mailservice.mailservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    @Autowired
    EmailMailSender mailSender;
    @Autowired
    UserService userService;

    @PostMapping("/test-mail")
    public String sendMail(){
        mailSender.sendMailtest("ekremozturk1997@gmail.com","test","test message");
        return "yollandı";
    }

    @PostMapping("/save-user")
    public String saveTest(@RequestParam("id") Long id, @RequestParam("email") String email){
        userService.save(id,email);
        return "saved";
    }
    @PostMapping("/confirmation-mail")
    public String sendConfirmationMail(@RequestParam("email") String email, @RequestParam("token") String token){
        userService.sendConfirmationMail(email,token);
        return "saved";
    }

}
