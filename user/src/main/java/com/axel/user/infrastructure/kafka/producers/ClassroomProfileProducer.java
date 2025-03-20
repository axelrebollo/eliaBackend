package com.axel.user.infrastructure.kafka.producers;

import com.axel.user.infrastructure.JpaEntities.ProfileEntity;
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
public class ClassroomProfileProducer {
    //injection dependencies
    private final KafkaTemplate<String, String> kafkaTemplate;  //used to send messages
    private final ObjectMapper objectMapper = new ObjectMapper();

    //Constructor
    public ClassroomProfileProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendData(String token, ProfileEntity profile, String role, String correlationId){
        //topic
        String topic = "response-data-classroomProfile";

        //headers
        Headers headers = new RecordHeaders();
        headers.add(new RecordHeader("kafka_correlationId", correlationId.getBytes()));

        //creates JSON data to send
        Map<String, String> data = new HashMap<>();
        data.put("idProfile", String.valueOf(profile.getId()));
        data.put("role", role);
        data.put("name", profile.getName());
        data.put("surname1", profile.getSurname1());
        data.put("surname2", profile.getSurname2());

        String jsonMessage;
        try {
            //convert data (Map<String, String>) to Json (Jackson library)
            jsonMessage = objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            throw new InfrastructureException("Error al serializar el mensaje JSON", e);
        }

        //creates message
        ProducerRecord<String, String> responseRecord = new ProducerRecord<>(
                topic,                          //topic
                null,                           //partition
                null,                           //timestamp
                token,                          //key
                jsonMessage,                    //value data en Json
                headers                         //headers
        );

        //send to kafka this message
        kafkaTemplate.send(responseRecord);
    }
}
