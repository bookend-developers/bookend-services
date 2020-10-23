package com.bookend.messageservice.service;

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
	public void deleteMessage(Message message) {
		messageRepository.delete(message);
	}


}
