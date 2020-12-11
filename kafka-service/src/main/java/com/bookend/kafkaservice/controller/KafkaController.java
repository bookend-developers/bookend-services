package com.bookend.kafkaservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.bookend.kafkaservice.kafka.Consumer;


@RestController
@RequestMapping("/kafka")
public class KafkaController {

	private Consumer consumer;
	private List<String> displayList = new ArrayList<>();
	
    @GetMapping("/displayEvents")
    public List<String> getBookInfo() {
    	consumer = new Consumer();
    	displayList = consumer.getDisplayList();	
    	System.out.print("??");
    	
        if(displayList.size()==0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The book does not exist.");
        }
        return displayList;

    }
}
