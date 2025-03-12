package com.axel.notebook.infrastructure.kafka.producers;

import com.axel.notebook.application.services.producers.ISubjectProducer;
import org.springframework.stereotype.Service;
import com.axel.notebook.infrastructure.kafka.consumers.SubjectConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class SubjectProducer implements ISubjectProducer {
    //Dependency injection
    private final SubjectConsumer subjectConsumer;
    private final KafkaTemplate<String, String> kafkaTemplate;

    //Constructor
    public SubjectProducer(final SubjectConsumer subjectConsumer, final KafkaTemplate<String, String> kafkaTemplate) {
        this.subjectConsumer = subjectConsumer;
        this.kafkaTemplate = kafkaTemplate;
    }

    //Petition with token about idProfile
    public int sendToken(String token) {
        //topic
        String topic = "petition-idProfile-subject";
        //Number unique id
        String correlationId = UUID.randomUUID().toString();
        //Create a promise to wawit response
        CompletableFuture<Integer> future = subjectConsumer.createFuture(correlationId);
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
}
