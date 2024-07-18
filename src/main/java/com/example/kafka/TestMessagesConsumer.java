package com.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TestMessagesConsumer {

    private static final Logger log = LoggerFactory.getLogger(TestMessagesConsumer.class);
    private final Map<String, String> messages = new HashMap<>();

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void messageListener(ConsumerRecord<String, String> message) {
        messages.put(message.key(), message.value());
        log.info("Topic: {}. Key: {}. Value: {}", message.topic(), message.key(), message.value());
    }

    public String getMessage(String key) {
        return messages.get(key);
    }
}
