package com.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TestMessagesConsumer {

    private final Map<String, String> messages = new HashMap<>();

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessages(ConsumerRecord<String, String> message){
        messages.put(message.key(), message.value());
    }

    public Map<String, String> getMessages() {
        return messages;
    }
}
