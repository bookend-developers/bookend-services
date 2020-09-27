package com.bookend.bookservice.model;

public class KafkaMessage<T> {
    private String topic;
    private T message;

    public KafkaMessage(String topic, T message) {
        this.topic = topic;
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public T getMessage() {
        return message;
    }
}
