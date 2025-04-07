package com.axel.notebook.infrastructure.kafka.producers;

import com.axel.notebook.application.services.producers.ICourseProducer;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.kafka.consumers.CourseConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class CourseProducer implements ICourseProducer {
    //Dependency injection
    private final CourseConsumer courseConsumer;
    private final KafkaTemplate<String, String> kafkaTemplate;

    //constructor
    public CourseProducer(final CourseConsumer courseConsumer, final KafkaTemplate<String, String> kafkaTemplate) {
        this.courseConsumer = courseConsumer;
        this.kafkaTemplate = kafkaTemplate;
    }

    //petition with token about idProfile
    public int sendToken(String token){
        //topic
        String topic = "petition-idProfile-course";
        //Number unique id
        String correlationId = UUID.randomUUID().toString();
        //Create a promise to wait response
        CompletableFuture<Integer> future = courseConsumer.createFuture(correlationId);
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
        } catch (Exception e) {
            throw new InfrastructureException("Error desde el microservicio user", e);
        }
    }
}
