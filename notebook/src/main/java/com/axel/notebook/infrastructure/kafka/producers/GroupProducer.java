package com.axel.notebook.infrastructure.kafka.producers;

import com.axel.notebook.application.services.producers.IGroupProducer;
import com.axel.notebook.infrastructure.kafka.consumers.GroupConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class GroupProducer implements IGroupProducer {
    //Dependency injection
    private final GroupConsumer groupConsumer;
    private final KafkaTemplate<String, String> kafkaTemplate;

    //Constructor
    public GroupProducer(GroupConsumer groupConsumer, KafkaTemplate<String, String> kafkaTemplate){
        this.groupConsumer = groupConsumer;
        this.kafkaTemplate = kafkaTemplate;
    }

    //Petition with token about idProfile
    public int sendToken(String token) {
        //Topic
        String topic = "petition-idProfile-group";
        //Number unique id
        String correlationId = UUID.randomUUID().toString();
        //Create a promise to wait response
        CompletableFuture<Integer> future = groupConsumer.createFuture(correlationId);
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
