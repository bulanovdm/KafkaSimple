package com.example.kafkalearn.kafka_test;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

import static com.example.kafkalearn.kafka_test.KafkaTest.test_topic;

@Component
class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    private CountDownLatch latch = new CountDownLatch(1);
    private String payload = "";

    @KafkaListener(topics = {test_topic})
    public void receive(@NotNull ConsumerRecord<Integer, String> consumerRecord) {
        log.info("received payload='{}'", consumerRecord.value());
        payload = consumerRecord.value();
        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public String getPayload() {
        return payload;
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
