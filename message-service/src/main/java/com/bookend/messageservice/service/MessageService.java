package com.bookend.messageservice.service;

import java.util.List;

import com.bookend.messageservice.exception.MandatoryFieldException;
import com.bookend.messageservice.exception.MessageNotFound;
import com.bookend.messageservice.exception.UserNotFound;
import com.bookend.messageservice.model.Message;
import org.springframework.stereotype.Service;
/**
 * MS-MSC stands for MessageService-MessageServiceClass
 * SM stands for ServiceMethod
 */
public interface MessageService {
	/**
	 * MS-MSC-1 (SM_54)
	 */
	 Message getById(String id) throws MessageNotFound;
	/**
	 * MS-MSC-2 (SM_55)
	 */
	 List<Message> findMessageByReceiver(String userName) throws MessageNotFound;
	/**
	 * MS-MSC-3 (SM_56)
	 */
	 List<Message> findMessageBySender(String userName) throws MessageNotFound;
	/**
	 * MS-MSC-4 (SM_57)
	 */
	 Message saveOrUpdate(Message message) throws MandatoryFieldException;
	/**
	 * MS-MSC-5 (SM_58)
	 */
	 void deleteMessage(Message messageId, String username) throws UserNotFound, MessageNotFound;
	/**
	 * MS-MSC-6 (SM_59)
	 */
	 List<Message> findChatByUserName(String userName,String userName2) throws MessageNotFound;
}
