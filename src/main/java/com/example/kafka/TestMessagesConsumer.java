package com.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class TestMessagesConsumer {

    private static final Logger log = LoggerFactory.getLogger(TestMessagesConsumer.class);
    private final ConcurrentHashMap<String, String> messages = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CountDownLatch> latches = new ConcurrentHashMap<>();

    @Value("${spring.kafka.consumer.wait-timeout}")
    private long defaultTimeout;

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void messageListener(ConsumerRecord<String, String> message) {
        messages.put(message.key(), message.value());
        if (latches.containsKey(message.key())) {
            latches.get(message.key()).countDown();
        }
        log.info("Topic: {}. Key: {}. Value: {}", message.topic(), message.key(), message.value());
    }

    public String getMessage(String key) throws InterruptedException {
        return getMessage(key, defaultTimeout, TimeUnit.MILLISECONDS);
    }

    public String getMessage(String key, long timeout, TimeUnit unit) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        latches.put(key, latch);
        boolean messageReceived = latch.await(timeout, unit);
        latches.remove(key);
        return messageReceived ? messages.get(key) : null;
    }
}
