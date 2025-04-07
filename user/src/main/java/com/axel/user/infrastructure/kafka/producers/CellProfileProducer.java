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
public class CellProfileProducer {
    //injection dependencies
    private final KafkaTemplate<String, String> kafkaTemplate;  //used to send messages
    private final ObjectMapper objectMapper = new ObjectMapper();

    //Constructor
    public CellProfileProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendData(String token, ProfileEntity profile, String role, String correlationId){
        //topic
        String topic = "response-data-cell";

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

    public void sendProfile(ProfileEntity profile, String correlationId){
        //topic
        String topic = "response-profile-cell";

        //headers
        Headers headers = new RecordHeaders();
        headers.add(new RecordHeader("kafka_correlationId", correlationId.getBytes()));

        //creates JSON data to send
        Map<String, String> data = new HashMap<>();
        data.put("idProfile", String.valueOf(profile.getId()));
        data.put("name", profile.getName() + ",");
        data.put("surname1", profile.getSurname1() + ",");
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
                null,                           //key
                jsonMessage,                    //value data en Json
                headers                         //headers
        );

        //send to kafka this message
        kafkaTemplate.send(responseRecord);
    }

    public void sendError(String correlationId, String errorMessage) {
        String topic = "response-data-cell";

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
