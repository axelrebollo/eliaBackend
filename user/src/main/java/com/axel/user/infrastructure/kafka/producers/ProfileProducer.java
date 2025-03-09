package com.axel.user.infrastructure.kafka.producers;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProfileProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ProfileProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProfileId(String token, int profileId){
        String topic = "profile-response-topic";
        ProducerRecord<String, String> responseRecord = new ProducerRecord<>(topic,
                token, String.valueOf(profileId));
        kafkaTemplate.send(responseRecord);
    }
}