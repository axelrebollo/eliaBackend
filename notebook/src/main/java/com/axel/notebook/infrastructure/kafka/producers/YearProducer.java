package com.axel.notebook.infrastructure.kafka.producers;

import com.axel.notebook.application.services.IYearProducer;
import com.axel.notebook.infrastructure.kafka.consumers.YearConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class YearProducer implements IYearProducer {
    //Dependency injection
    private final YearConsumer yearConsumer;
    private final KafkaTemplate<String, String> kafkaTemplate;

    //Constructor
    public YearProducer(YearConsumer yearConsumer, KafkaTemplate<String, String> kafkaTemplate) {
        this.yearConsumer = yearConsumer;
        this.kafkaTemplate = kafkaTemplate;
    }

    //Petition with token about idProfile
    public int sendToken(String token) {
        String topic = "token-topic";
        CompletableFuture<Integer> future = yearConsumer.createFuture(token);

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, token);

        kafkaTemplate.send(record);

        return future.join();
    }
}
