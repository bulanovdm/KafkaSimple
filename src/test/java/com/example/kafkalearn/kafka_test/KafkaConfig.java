package com.example.kafkalearn.kafka_test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.Map;

@EnableKafka
@TestConfiguration
public class KafkaConfig {

    @Autowired
    // bean created with @EmbeddedKafka
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Bean
    public KafkaTemplate<Integer, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    ProducerFactory<Integer, String> producerFactory() {
        Map<String, Object> stringObjectMap = KafkaTestUtils.producerProps(embeddedKafkaBroker);
        return new DefaultKafkaProducerFactory<>(stringObjectMap);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    ConsumerFactory<Integer, String> consumerFactory() {
        Map<String, Object> stringObjectMap
                = KafkaTestUtils.consumerProps(embeddedKafkaBroker.getBrokersAsString(), "true", embeddedKafkaBroker);
        return new DefaultKafkaConsumerFactory<>(stringObjectMap);
    }
}
