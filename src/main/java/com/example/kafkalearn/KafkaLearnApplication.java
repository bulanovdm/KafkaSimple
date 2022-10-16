package com.example.kafkalearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaLearnApplication {
    final public static String MY_TOPIC = "MY_TOPIC";

    public static void main(String[] args) {
        SpringApplication.run(KafkaLearnApplication.class, args);
    }
}
