package com.bookclupservice.bookclubservice.kafka;

import com.bookclupservice.bookclubservice.model.Member;
import com.bookclupservice.bookclubservice.payload.MailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;



    public void sendMailRequest(MailRequest mailRequest) {

        LOGGER.info("sending book='{}' to topic='{}'", mailRequest, "Mail");
        kafkaTemplate.send("Mail", mailRequest);
    }
}
