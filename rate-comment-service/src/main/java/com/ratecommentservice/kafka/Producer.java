package com.ratecommentservice.kafka;

import com.ratecommentservice.payload.KafkaMessage;
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

    public void publishNewComment(KafkaMessage kafkaMessage) {
        LOGGER.info("sending comment='{}' to topic='{}'", kafkaMessage.getMessage(), kafkaMessage.getTopic());
        kafkaTemplate.send(kafkaMessage.getTopic(), kafkaMessage.getMessage());
    }
    public void publishNewRate(KafkaMessage kafkaMessage) {
        LOGGER.info("sending rate='{}' to topic='{}'", kafkaMessage.getMessage(), kafkaMessage.getTopic());
        kafkaTemplate.send(kafkaMessage.getTopic(), kafkaMessage.getMessage());
    }


}
