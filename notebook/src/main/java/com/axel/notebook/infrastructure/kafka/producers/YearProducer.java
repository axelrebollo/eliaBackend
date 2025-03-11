package com.axel.notebook.infrastructure.kafka.producers;

import com.axel.notebook.application.services.IYearProducer;
import com.axel.notebook.infrastructure.kafka.consumers.YearConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class YearProducer implements IYearProducer {
    //Dependency injection
    private final YearConsumer yearConsumer;    //usage to create a promise that waiting response
    private final KafkaTemplate<String, String> kafkaTemplate;  //objet to send messages

    //Constructor
    public YearProducer(YearConsumer yearConsumer, KafkaTemplate<String, String> kafkaTemplate) {
        this.yearConsumer = yearConsumer;
        this.kafkaTemplate = kafkaTemplate;
    }

    //Petition with token about idProfile
    public int sendToken(String token) {
        //Topic
        String topic = "petition-idProfile-year";
        //Number unique id
        String correlationId = UUID.randomUUID().toString();
        //Create a promise to wait response
        CompletableFuture<Integer> future = yearConsumer.createFuture(correlationId);
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
