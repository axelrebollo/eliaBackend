package com.axel.notebook.infrastructure.kafka.producers;

import com.axel.notebook.application.services.producers.IClassroomProfileProducer;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.kafka.consumers.ClassroomProfileConsumer;
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
public class ClassroomProfileProducer implements IClassroomProfileProducer {
    //Dependency injection
    private final ClassroomProfileConsumer classroomProfileConsumer;
    private final KafkaTemplate<String, String> kafkaTemplate;

    //Constructor
    public ClassroomProfileProducer(final ClassroomProfileConsumer classroomProfileConsumer, final KafkaTemplate<String, String> kafkaTemplate) {
        this.classroomProfileConsumer = classroomProfileConsumer;
        this.kafkaTemplate = kafkaTemplate;
    }

    //petition with token about data token
    public Map<String, String> sendToken(String token) {
        //topic
        String topic = "petition-data-classroomProfile";
        //Number unique id
        String correlationId = UUID.randomUUID().toString();
        //Create a promise to wait response
        CompletableFuture<Map<String, String>> future = classroomProfileConsumer.createFuture(correlationId);
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

        Map<String, String> response = future.join();

        //check if exit an error
        if ("error".equalsIgnoreCase(response.get("status"))) {
            throw new InfrastructureException("Error desde el microservicio user: " + response.get("message"));
        }

        return response;
    }
}
