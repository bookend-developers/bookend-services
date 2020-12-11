package com.bookend.authorservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import com.bookend.authorservice.model.KafkaMessage;


public class Producer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;



    public void bookPublished(KafkaMessage kafkaMessage) {
        LOGGER.info("sending book='{}' to topic='{}'", kafkaMessage.getMessage(), kafkaMessage.getTopic());
        kafkaTemplate.send(kafkaMessage.getTopic(), kafkaMessage.getMessage());
    }
    public void bookDeleted(KafkaMessage kafkaMessage) {
        LOGGER.info("sending book='{}' to topic='{}'", kafkaMessage.getMessage(), kafkaMessage.getTopic());
        kafkaTemplate.send(kafkaMessage.getTopic(), kafkaMessage.getMessage());
    }


}
