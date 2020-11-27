package com.bookend.messageservice.service;

import java.util.List;

import com.bookend.messageservice.model.Message;
import org.springframework.stereotype.Service;

public interface MessageService {
	 Message getById(String id);
	 List<Message> findMessageByReceiver(String userName);
	 List<Message> findMessageBySender(String userName);
	 Message saveOrUpdate(Message message);
	 void deleteMessage(Message messageId,String username);
	 List<Message> findChatByUserName(String userName,String userName2);
}
