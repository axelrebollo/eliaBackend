package com.axel.notebook.infrastructure.kafka.consumers;

import com.axel.notebook.application.services.consumers.IYearConsumer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class YearConsumer implements IYearConsumer {
    //Map to save pending requests
    private static final ConcurrentMap<String, CompletableFuture<Integer>> pendingRequests = new ConcurrentHashMap<>();

    @Bean
    public KafkaAdmin.NewTopics createYearTopics() {
        return new KafkaAdmin.NewTopics(
                new NewTopic("years", 1, (short) 1)
        );
    }

    //create a response to process and sent after
    public CompletableFuture<Integer> createFuture(String correlationId) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        pendingRequests.put(correlationId, future);
        return future;
    }

    //Listen to "response-idProfile-year", only one instance from group is processed
    @KafkaListener(topics = "response-idProfile-year", groupId = "year-group-consumer")
    public void receiveProfileId(ConsumerRecord<String, String> record) {
        //extract headers
        Headers headers = record.headers();
        Header correlationIdHeader = headers.lastHeader("kafka_correlationId");

        if (correlationIdHeader == null) {
            throw new IllegalStateException("No hay id de correlacion en el mensaje de respuesta");
        }
        String correlationId = new String(correlationIdHeader.value());

        //get profileId
        Integer profileId = Integer.parseInt(record.value());

        //delete pending request and finalize process
        CompletableFuture<Integer> future = pendingRequests.remove(correlationId);
        //check if request exist
        if (future != null) {
            future.complete(profileId);
        }
    }
}
