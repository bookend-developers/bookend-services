package com.bookend.kafkaservice.kafka;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
	
	public Consumer() {
		super();
		// TODO Auto-generated constructor stub
	}


	private static List<String> displayList = new ArrayList<>();
	
    public List<String> getDisplayList() {
		return displayList;
	}

	public void setDisplayList(List<String> displayList) {
		this.displayList = displayList;
	}

	@KafkaListener(topics = "adding-book",
            groupId ="bookend")
    public void consumeBook(String message) {
        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        try {
          Map<String,String> msg = mapper.readValue(message,Map.class);
          String author = msg.get("author");
          String bookId= msg.get("book").toString();
          String display = "bookend-authorservice --> add book with author id= "+author + ", book id= "+bookId;
          displayList.add(display);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    @KafkaListener(topics = "deleting-book",
            groupId ="bookend")
    public void deleteBook(String message){

        System.out.println(message);
        String[] splited = message.split("\"");
        String bookId = message;
        String display = "bookend-authorservice --> delete book with book id= "+bookId;
        displayList.add(display);

    }
    
    @KafkaListener(topics = "new-comment",
            groupId ="bookend")
    public void newComment(String message){

        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String,String> msg = mapper.readValue(message,Map.class);
            String book = msg.get("book");
            String comment = msg.get("comment");
            String display = "bookend-bookservice --> add new-comment with comment id= "+comment+ ", bookId= "+book;
            displayList.add(display);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    @KafkaListener(topics = "new-rate",
            groupId ="bookend")
    public void newRate(String message){

        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String,String> msg = mapper.readValue(message,Map.class);

            String book = msg.get("book");
            String rate = msg.get("rate");
            String display = "bookend-bookservice --> update average rate of book with id "+book+ " new average rate = "+rate;
            displayList.add(display);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    @KafkaListener(topics = "deleting-book",
            groupId ="bookend")
    public void consumeBoook(String message) {
        System.out.println(message);
        String[] splited = message.split("\"");
        String bookId = message;
        String display = "bookend-rate-commentservice --> delete rates and comments of book with id "+bookId;
        displayList.add(display);
    }
    
    /*
    @KafkaListener(topics = "comment",
            groupId ="bookend-rate-commentservice")
    public void consumeComment(String message) {
        System.out.println(message);
        String[] splited = message.split("\"");
        ObjectMapper mapper = new ObjectMapper();
        try {
            PostCommentRequest commentRequest = mapper.readValue(message, PostCommentRequest.class);
            PostComment postComment  = new PostComment(commentRequest.getPostID()
                                                    ,commentRequest.getUsername()
                                                    ,commentRequest.getComment());
            postCommentService.commentPost(postComment);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }*/
    
    
}
