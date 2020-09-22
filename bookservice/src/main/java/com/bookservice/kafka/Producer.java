package com.bookservice.kafka;

import com.bookservice.model.Book;
import com.bookservice.model.KafkaMessage;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;



    public void publishBook(KafkaMessage kafkaMessage) {
        LOGGER.info("sending book='{}' to topic='{}'", kafkaMessage.getMessage(), kafkaMessage.getTopic());
        kafkaTemplate.send(kafkaMessage.getTopic(), kafkaMessage.getMessage());
    }


}
