package com.axel.user.infrastructure.kafka.producers;

import com.axel.user.infrastructure.exceptions.InfrastructureException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class CourseProfileProducer {
    //injection dependencies
    private final KafkaTemplate<String, String> kafkaTemplate;  //used to send messages
    private final ObjectMapper objectMapper = new ObjectMapper();

    //Constructor
    public CourseProfileProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProfileId(String token, int profileId, String correlationId){
        //topic
        String topic = "response-idProfile-course";

        //headers
        Headers headers = new RecordHeaders();
        headers.add(new RecordHeader("kafka_correlationId", correlationId.getBytes()));

        //creates message
        ProducerRecord<String, String> responseRecord = new ProducerRecord<>(
                topic,                          //topic
                null,                           //partition
                null,                           //timestamp
                token,                          //key
                String.valueOf(profileId),      //value
                headers                         //headers
        );

        //send to kafka this message
        kafkaTemplate.send(responseRecord);
    }

    public void sendError(String correlationId, String errorMessage) {
        String topic = "response-idProfile-course";

        Headers headers = new RecordHeaders();
        headers.add(new RecordHeader("kafka_correlationId", correlationId.getBytes()));

        Map<String, String> errorData = new HashMap<>();
        errorData.put("status", "error");
        errorData.put("message", errorMessage);

        String jsonMessage;
        try {
            jsonMessage = objectMapper.writeValueAsString(errorData);
        } catch (Exception e) {
            throw new InfrastructureException("Error al serializar el mensaje JSON de error", e);
        }

        ProducerRecord<String, String> responseRecord = new ProducerRecord<>(
                topic,
                null,
                null,
                null,
                jsonMessage,
                headers
        );

        kafkaTemplate.send(responseRecord);
    }
}
