package com.axel.notebook.infrastructure.kafka.producers;

import com.axel.notebook.application.services.producers.ITableProducer;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.kafka.consumers.TableConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class TableProducer implements ITableProducer {
    //Dependency injection
    private final TableConsumer tableConsumer;
    private final KafkaTemplate<String, String> kafkaTemplate;

    //constructor
    public TableProducer(final TableConsumer tableConsumer, final KafkaTemplate<String, String> kafkaTemplate) {
        this.tableConsumer = tableConsumer;
        this.kafkaTemplate = kafkaTemplate;
    }

    //petition with token about idProfile
    public int sendToken(String token){
        //topic
        String topic = "petition-idProfile-table";
        //Number unique id
        String correlationId = UUID.randomUUID().toString();
        //Create a promise to wait response
        CompletableFuture<Integer> future = tableConsumer.createFuture(correlationId);
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

        try {
            return future.join();
        }
        catch (Exception e) {
            throw new InfrastructureException("Error desde el microservicio user", e);
        }
    }

}
