package com.bookend.bookclubservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
class KafkaTopicConfig {

    @Bean
    public NewTopic userRegistered() {
        return TopicBuilder.name("user-registered").build();
    }

    @Bean
    public NewTopic comment() {
        return TopicBuilder.name("comment").build();
    }




}
