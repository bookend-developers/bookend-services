package com.bookend.bookclubservice.controller;

import com.bookend.bookclubservice.kafka.MessageProducer;
import com.bookend.bookclubservice.payload.MailRequest;
import com.bookend.bookclubservice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class Test {

    @Autowired
    MessageProducer messageProducer;
    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/test")
    public String testGet(){

        return "sdfdsgds";
    }

    @GetMapping("/test-kafka")
    public void testkafka(){
        MailRequest mailRequest = new MailRequest((long)1,"Test Request","Test Text");

        messageProducer.sendMailRequest(mailRequest);
    }
    @GetMapping("/current-user")
    public ResponseEntity getInbox(OAuth2Authentication auth){

        return ResponseEntity.ok(memberRepository.findByUserName(auth.getName()));

    }

}
