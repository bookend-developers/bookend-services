package com.bookend.messageservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bookend.messageservice.model.Message;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<Message, String>  {
	Message findMessageById(String id);
	List<Message>  findMessageBySender(String sender);
	List<Message> findMessageByReceiver(String receiver);

}
