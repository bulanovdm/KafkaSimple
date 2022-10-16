package com.example.kafkalearn.kafka_test;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
class KafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    final private KafkaTemplate<Integer, String> kafkaTemplate;

    private KafkaProducer(KafkaTemplate<Integer, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, String payload) {
        log.info("sending payload='{}' to topic='{}'", payload, topic);
        ProducerRecord<Integer, String> record
                = new ProducerRecord<>(topic, payload);
        kafkaTemplate.send(record);
    }
}
