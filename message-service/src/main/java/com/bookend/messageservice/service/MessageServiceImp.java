package com.bookend.messageservice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	public Message getById(String id) {
		return messageRepository.findMessageById(id);
	}

	public List<Message> findMessageByReceiver(String userName){
		return messageRepository.findMessageByReceiver(userName);
		
	}
	
	public List<Message> findMessageBySender(String userName){
		return messageRepository.findMessageBySender(userName);
		
	}
	
	public Message saveOrUpdate(Message message) {
		return messageRepository.save(message);
	}

	@Override
	public void deleteMessage(Message message,String username) {
		if(message.getReceiver().equals(username)){
			message.setReceiver(null);
		}
		if(message.getSender().equals(username)){
			message.setSender(null);
		}
		messageRepository.save(message);
	}
	
	public List<Message> findChatByUserName(String userName1,String userName2){
		
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
