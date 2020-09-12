package com.bookend.bookproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	@Autowired
    KafkaTemplate<String,Book> kafkaTemplate;
	
	private static final String TOPIC = "NewTopic";
	
	//@CrossOrigin(origins = "http://localhost:8081")
	@PostMapping("/publish")
	@PreAuthorize("hasAuthority('create_profile')")
	public String publishMessage(@RequestBody Book book) {
		
		kafkaTemplate.send(TOPIC,book);
		return "Published Succesfully";
		
	}
}
