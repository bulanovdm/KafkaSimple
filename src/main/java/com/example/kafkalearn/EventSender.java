package com.example.kafkalearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.Instant;

import static com.example.kafkalearn.KafkaLearnApplication.MY_TOPIC;

@Component
public class EventSender {

    private static final Logger log = LoggerFactory.getLogger(EventSender.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public EventSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Async
    @Scheduled(initialDelay = 50000, fixedDelay = 1000)
    public void sendEvent() {
        Message<String> message = null;
        try {
            message = MessageBuilder.withPayload("What a lovely day. Now is: " + Instant.now())
                    .setHeader(KafkaHeaders.MESSAGE_KEY, "GREETING")
                    .setHeader(KafkaHeaders.TOPIC, MY_TOPIC)
                    //.setHeader("MYFUNKYHEADER", "hi!")
                    .build();
            log.info("Create event");
            //Message<String> finalMessage = message;
            kafkaTemplate.send(message).addCallback(new ListenableFutureCallback<>() {
                @Override public void onSuccess(SendResult<String, String> result) {
                    log.info("Producer record: " + result.getProducerRecord());
                    log.info("Producer metadata: " + result.getRecordMetadata().toString());
                }
                @Override public void onFailure(Throwable ex) {
                    log.error(ex.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            handleError(message, e);
        }
    }

    void handleError(Message<String>  s, Throwable e) {

    }
}
