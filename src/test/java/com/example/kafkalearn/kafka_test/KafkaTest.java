package com.example.kafkalearn.kafka_test;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DirtiesContext
@SpringBootTest()
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@ContextConfiguration(classes = {KafkaConsumer.class, KafkaProducer.class, KafkaConfig.class})
public class KafkaTest {

    private static final Logger log = LoggerFactory.getLogger(KafkaTest.class);

    private final KafkaConsumer consumer;
    private final KafkaProducer producer;

    public static final String test_topic = "embedded-test-topic";

    @Autowired
    private KafkaTest(KafkaConsumer consumer, KafkaProducer producer) {
        this.consumer = consumer;
        this.producer = producer;
    }

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived()
            throws Exception {
        String data = "Sending with our own simple KafkaProducer";

        producer.send(test_topic, data);

        boolean messageConsumed = consumer.getLatch().await(5, TimeUnit.SECONDS);
        assertTrue(messageConsumed);
        assertEquals(consumer.getLatch().getCount(), 0);
        assertThat(data, containsString(consumer.getPayload()));
    }
}
