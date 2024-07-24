package com.example.kafka;


import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class TestMessageProducer {

    private static final Logger log = LoggerFactory.getLogger(TestMessageProducer.class);
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic}")
    private String topic;

    public SendResult<String, String> sendMessage(String key, String value) {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, value);
        return sendMessage(producerRecord);
    }

    public SendResult<String, String> sendMessage(String value) {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, value);
        return sendMessage(producerRecord);
    }

    private SendResult<String, String> sendMessage(ProducerRecord<String, String> producerRecord) {
        try {
            SendResult<String, String> sendResult = kafkaTemplate.send(producerRecord).get();
            log.info("Message was sent:{}", sendResult);
            return sendResult;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
