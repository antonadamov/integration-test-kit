package com.example.kafka;


import com.example.exception.AsyncTestingFrameworkException;
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

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public TestMessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${spring.kafka.topic}")
    private String topic;

    public SendResult<String, String> sendMessage(String key, String value) throws AsyncTestingFrameworkException {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, value);
        return sendMessage(producerRecord);
    }

    public SendResult<String, String> sendMessage(String value) throws AsyncTestingFrameworkException {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, value);
        return sendMessage(producerRecord);
    }

    private SendResult<String, String> sendMessage(ProducerRecord<String, String> producerRecord) throws AsyncTestingFrameworkException {
        try {
            SendResult<String, String> sendResult = kafkaTemplate.send(producerRecord).get();
            log.info("Message was sent:{}", sendResult);
            return sendResult;
        } catch (ExecutionException e) {
            throw new AsyncTestingFrameworkException("Exception occurred while sending Kafka message", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AsyncTestingFrameworkException("Execution was interrupted while sending Kafka message", e);
        }
    }
}
