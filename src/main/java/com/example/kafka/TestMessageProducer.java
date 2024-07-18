package com.example.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.ExecutionException;

public class TestMessageProducer {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Value("spring.kafka.topic")
    private String topic;

    public void sendMessage(String str){
        try {
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, str);
            SendResult<String, String> sendResult = kafkaTemplate.send(producerRecord).get();
            System.out.println("Message was sent:" + sendResult);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

    }
}
