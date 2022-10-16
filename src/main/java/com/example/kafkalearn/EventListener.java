package com.example.kafkalearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import static com.example.kafkalearn.KafkaLearnApplication.MY_TOPIC;
import static org.springframework.kafka.support.KafkaHeaders.MESSAGE_KEY;

@Component
@KafkaListener(topics = {MY_TOPIC})
public class EventListener {

    private static final Logger LOG = LoggerFactory.getLogger(EventListener.class);

    @KafkaHandler
    public void processEvent(String event, @Header(MESSAGE_KEY) String key) {
        LOG.info("Received event in listener: {}. With key: {}", event, key);
    }
}

