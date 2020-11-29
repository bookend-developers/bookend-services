package com.bookend.messageservice.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.bookend.messageservice.payload.MessageResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import com.bookend.messageservice.model.Message;
import com.bookend.messageservice.service.MessageService;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/message")
public class MessageController {

    private MessageService messageService;
    @Autowired
    public void setMessageService(MessageService messageService){
        this.messageService=messageService;
    }

    @ApiOperation(value = "Get message by id ", response = Message.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved message"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource")
    }
    )
    @GetMapping("/{messageid}")
    public Message getMessage(@PathVariable("messageid") String messageId) {
        return messageService.getById(messageId);
    }

    @ApiOperation(value = "View user's inbox message ", response = Message.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved message list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
    }
    )
    @GetMapping("/inbox")
    public List<Message> getInbox(OAuth2Authentication auth){

        List<Message> chat =  messageService.findMessageByReceiver(auth.getUserAuthentication().getName());
        Collections.sort(chat, (o1, o2) -> o1.getSendDate().compareTo(o2.getSendDate()));
        Collections.reverse(chat);
        
        return chat;
        
    }
    @ApiOperation(value = "View user's sent message ", response = Message.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved message list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
    }
    )
    @GetMapping("/sent")
    public List<Message> getSent(OAuth2Authentication auth){

    	List<Message> chat = messageService.findMessageBySender(auth.getUserAuthentication().getName());
        Collections.sort(chat, (o1, o2) -> o1.getSendDate().compareTo(o2.getSendDate()));
        Collections.reverse(chat);
        return chat;
    }
    @ApiOperation(value = "Send a message for a specific user", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully sending the message"),
            @ApiResponse(code = 401, message = "You are not authorized to send a message"),
            @ApiResponse(code = 400, message = "The way you are trying to send is not accepted.")
    }
    )

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
    @ApiOperation(value = "Delete a specific message")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted message"),
            @ApiResponse(code = 401, message = "You are not authorized to delete the resource")
    }
    )
    @DeleteMapping("/delete/{messageid}")
    public ResponseEntity<?> deleteMessage(@PathVariable("messageid")  String messageId,OAuth2Authentication auth){
        Message message = messageService.getById(messageId);
        if(message== null){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND,"Message is not found.");
        }
        messageService.deleteMessage(message,auth.getName());
        return ResponseEntity.ok(new MessageResponse("Successfully deleted."));
    }
    @ApiOperation(value = "View user's messages with another user ", response = Message.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved message list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
    }
    )

    @GetMapping("/chat/{userName}")
    public List<Message> getChat(OAuth2Authentication auth,@PathVariable("userName") String userName){
    	return messageService.findChatByUserName(auth.getName(),userName);
    }

}
