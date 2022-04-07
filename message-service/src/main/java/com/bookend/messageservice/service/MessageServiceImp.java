package com.bookend.messageservice.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.bookend.messageservice.exception.MandatoryFieldException;
import com.bookend.messageservice.exception.MessageNotFound;
import com.bookend.messageservice.exception.UserNotFound;
import com.bookend.messageservice.model.Message;
import com.bookend.messageservice.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImp implements MessageService{
	
	private MessageRepository messageRepository;
	@Autowired
	public void setMessageRepository(MessageRepository messageRepository){

		this.messageRepository=messageRepository;
	}

	@Override
	public Message getById(String id) throws MessageNotFound {
		Message message = messageRepository.findMessageById(id);
		if(message == null) {
			throw new MessageNotFound("Message does not exist.");
		}
		return message;
	}

	public List<Message> findMessageByReceiver(String userName) throws MessageNotFound {
		List<Message> message = messageRepository.findMessageByReceiver(userName);
		if(message == null){
			throw new MessageNotFound("Message does not exist.");
		}
		return message;
	}
	
	public List<Message> findMessageBySender(String userName) throws MessageNotFound {
		List<Message> message = messageRepository.findMessageBySender(userName);
		if(message == null){
			throw new MessageNotFound("Message does not exist.");
		}
		return message;
	}
	
	public Message saveOrUpdate(Message message) throws MandatoryFieldException {
		if(message.getSender()==null || message.getSender() == ""){
			throw new MandatoryFieldException("Sender's name cannot be empty.");
		}
		message.setSender(message.getSender());
		if(message.getReceiver()==null || message.getReceiver()== ""){
			throw new MandatoryFieldException("Receiver's name cannot be empty.");
		}
		message.setReceiver(message.getReceiver());
		if(message.getSubject()==null || message.getSubject() == ""){
			throw new MandatoryFieldException("Subject cannot be empty.");
		}
		message.setSubject(message.getSubject());
		if(message.getText()==null || message.getText() == ""){
			throw new MandatoryFieldException("Text cannot be empty.");
		}
		message.setText(message.getText());

		message.setSendDate(message.getSendDate());

		return messageRepository.save(message);
	}

	@Override
	public void deleteMessage(Message message, String username) throws UserNotFound, MessageNotFound {
		List<Message> messagesFromSender = messageRepository.findMessageBySender(username);
		List<Message> messagesFromReceiver = messageRepository.findMessageByReceiver(username);
		if((messagesFromSender==null || messagesFromSender.isEmpty()) && (messagesFromReceiver==null || messagesFromReceiver.isEmpty()) ){
			throw new UserNotFound("User does not found..");
		}
		Message msg = messageRepository.findMessageById(message.getId());
		if(msg == null){
			throw new MessageNotFound("Message does not found..");
		}
		messageRepository.delete(message);
	}
	
	public List<Message> findChatByUserName(String userName1,String userName2) throws MessageNotFound {
		
		 List<Message> sent =  this.findMessageBySender(userName1);
		 List<Message> chat = new ArrayList<Message>();
		 
		 for (int i = 0; i < sent.size(); i++) {
			Message msg = sent.get(i);
			if (msg.getReceiver().equalsIgnoreCase(userName2)) {
				
				chat.add(msg);
			}
		}
		 
		List<Message> recieved = this.findMessageBySender(userName2);
		 for (int i = 0; i < recieved.size(); i++) {
			Message msg = recieved.get(i);
			if (msg.getReceiver().equalsIgnoreCase(userName1)) {
				
				chat.add(msg);
			}
		}
		
		Collections.sort(chat, (o1, o2) -> o1.getSendDate().compareTo(o2.getSendDate()));
		Collections.reverse(chat);
		return chat;
		
	}



}
