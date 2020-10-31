package com.bookend.bookservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
class KafkaTopicConfig {

    @Bean
    public NewTopic addbook() {
        return TopicBuilder.name("adding-book").build();
    }

    @Bean
    public NewTopic deletebook() {
        return TopicBuilder.name("deleting-book").build();
    }

}
