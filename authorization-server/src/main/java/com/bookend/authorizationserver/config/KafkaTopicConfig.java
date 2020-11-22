package com.bookend.authorizationserver.config;

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
    public NewTopic confirmationMail() {
        return TopicBuilder.name("confirmation-mail").build();
    }
    
    @Bean
    public NewTopic resetPasswordMail() {
        return TopicBuilder.name("resetPassword-mail").build();
    }

}
