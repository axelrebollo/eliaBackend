package com.axel.user.infrastructure.kafka.consumers;

import com.axel.user.infrastructure.kafka.producers.ProfileProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TokenConsumer {
    private final ProfileProducer profileProducer;

    public TokenConsumer(ProfileProducer profileProducer) {
        this.profileProducer = profileProducer;
    }

    @KafkaListener(topics = "token-topic", groupId = "user-group")
    public void processToken(ConsumerRecord<String, String> record) {
        String token = record.value();
        int profileId = Math.abs(token.hashCode()); // Simulación de ID numérico

        profileProducer.sendProfileId(token, profileId); // Enviar respuesta al Notebook
    }
}
