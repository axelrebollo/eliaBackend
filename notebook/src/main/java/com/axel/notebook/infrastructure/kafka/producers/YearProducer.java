package com.axel.notebook.infrastructure.kafka.producers;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.*;

@Service
public class YearProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    //Inyection kafkaTemplate to send messages
    public YearProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    //Send token to kafka
    public void sendToken(String token) {
        //name of topic
        String topic = "token-topic";

        //Send message to Kafka
        kafkaTemplate.send(topic, token);
        //System.out.println("Token enviado: " + token);
    }
}
