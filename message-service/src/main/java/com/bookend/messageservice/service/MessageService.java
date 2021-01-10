package com.bookend.messageservice.service;

import java.util.List;

import com.bookend.messageservice.exception.MandatoryFieldException;
import com.bookend.messageservice.exception.MessageNotFound;
import com.bookend.messageservice.exception.UserNotFound;
import com.bookend.messageservice.model.Message;
import org.springframework.stereotype.Service;

public interface MessageService {
	 Message getById(String id) throws MessageNotFound;
	 List<Message> findMessageByReceiver(String userName) throws MessageNotFound;
	 List<Message> findMessageBySender(String userName) throws MessageNotFound;
	 Message saveOrUpdate(Message message) throws MandatoryFieldException;
	 void deleteMessage(Message messageId, String username) throws UserNotFound, MessageNotFound;
	 List<Message> findChatByUserName(String userName,String userName2) throws MessageNotFound;
}
