package com.bookend.messageservice.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "messages")
public class Message implements Comparable<Message> {
	
    @Id
    private String id;
    private String sender;
    private String receiver;
    private String subject;
	private String text;
	private Date sendDate;

	public Message() {
		super();

	}

	public Message(String id,String sender, String receiver, String subject, String text, Date sendDate) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		this.text = text;
		this.sendDate = sendDate;
	}
	public Message(String sender, String receiver, String subject, String text, Date sendDate) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		this.text = text;
		this.sendDate = sendDate;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	@Override
	public int compareTo(Message o) {
	    return getSendDate().compareTo(o.getSendDate());
	  }

}
