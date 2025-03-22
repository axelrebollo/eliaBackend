package com.axel.notebook.infrastructure.kafka.producers;

import com.axel.notebook.application.services.producers.ICellProducer;
import com.axel.notebook.infrastructure.kafka.consumers.CellConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class CellProducer implements ICellProducer {
    //Dependency injection
    private final CellConsumer cellConsumer;
    private final KafkaTemplate<String, String> kafkaTemplate;

    //Constructor
    public CellProducer(final CellConsumer cellConsumer, final KafkaTemplate<String, String> kafkaTemplate) {
        this.cellConsumer = cellConsumer;
        this.kafkaTemplate = kafkaTemplate;
    }

    //petition with token about data token
    public Map<String, String> sendToken(String token){
        //topic
        String topic = "petition-data-cell";
        //Number unique id
        String correlationId = UUID.randomUUID().toString();
        //Create a promise to wait response
        CompletableFuture<Map<String, String>> future = cellConsumer.createFuture(correlationId);
        //create headers
        Headers headers = new RecordHeaders();
        headers.add(new RecordHeader("kafka_correlationId", correlationId.getBytes()));
        //creates and send message to kafka
        ProducerRecord<String, String> record = new ProducerRecord<>(
                topic,              //topic
                null,               //partition
                null,               //timestamp
                null,               //key
                token,              //value
                headers             //headers
        );
        kafkaTemplate.send(record);

        //waiting response
        return future.join();
    }

    //petition with token about data token
    public Map<String, String> sendIdProfile(int idProfile){
        //topic
        String topic = "petition-profile-cell";
        //Number unique id
        String correlationId = UUID.randomUUID().toString();
        //Create a promise to wait response
        CompletableFuture<Map<String, String>> future = cellConsumer.createFuture(correlationId);
        //create headers
        Headers headers = new RecordHeaders();
        headers.add(new RecordHeader("kafka_correlationId", correlationId.getBytes()));

        String idProfileString = Integer.toString(idProfile);

        //creates and send message to kafka
        ProducerRecord<String, String> record = new ProducerRecord<>(
                topic,              //topic
                null,               //partition
                null,               //timestamp
                null,               //key
                idProfileString,    //value
                headers             //headers
        );
        kafkaTemplate.send(record);

        //waiting response
        return future.join();
    }
}
