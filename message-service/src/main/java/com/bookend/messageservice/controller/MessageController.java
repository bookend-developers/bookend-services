package com.bookend.messageservice.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import com.bookend.messageservice.model.Message;
import com.bookend.messageservice.service.MessageService;


@RestController
@RequestMapping("/api/message")
public class MessageController {

    private MessageService messageService;
    @Autowired
    public void setMessageService(MessageService messageService){
        this.messageService=messageService;
    }

    @GetMapping("/{messageid}")
    public Message getMessage(@PathVariable("messageid") String messageId) {
        return messageService.getById(messageId);
    }

    @GetMapping("/inbox")
    public List<Message> getInbox(OAuth2Authentication auth){

        List<Message> chat =  messageService.findMessageByReceiver(auth.getUserAuthentication().getName());
        Collections.sort(chat, (o1, o2) -> o1.getSendDate().compareTo(o2.getSendDate()));
        
        return chat;
        
    }
    
    @GetMapping("/sent")
    public List<Message> getSent(OAuth2Authentication auth){

    	List<Message> chat = messageService.findMessageBySender(auth.getUserAuthentication().getName());
        Collections.sort(chat, (o1, o2) -> o1.getSendDate().compareTo(o2.getSendDate()));
        
        return chat;
    }
    
    @PostMapping("/new/{receiverUser}")
    public ResponseEntity<String> sendMessage(@PathVariable("receiverUser") String receiver, @RequestBody Message message, OAuth2Authentication auth ){
        message.setReceiver(receiver);
        message.setSender(auth.getName());
        message.setSubject(message.getSubject());
        message.setText(message.getText());
        message.setSendDate(new Date());
    	messageService.saveOrUpdate(message);
        return ResponseEntity.ok("Message is sent");
    }

    @DeleteMapping("/delete/{messageid}")
    public void deleteShelf(@PathVariable("messageid")  String messageId){
        messageService.deleteMessage(messageService.getById(messageId));
    }
    
    @GetMapping("/chat/{userName}")
    public List<Message> getChat(OAuth2Authentication auth,@PathVariable("userName") String userName){
    	return messageService.findChatByUserName(auth.getName(),userName);
    }

}
