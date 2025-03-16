package com.axel.user.infrastructure.kafka.producers;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TableProfileProducer {
    //Dependency injection
    private final KafkaTemplate<String, String> kafkaTemplate;  //used to send messages

    //Constructor
    public TableProfileProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProfileId(String token, int profileId, String correlationId){
        //topic
        String topic = "response-idProfile-table";

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

}
