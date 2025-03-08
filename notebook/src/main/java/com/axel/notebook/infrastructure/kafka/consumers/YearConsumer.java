package com.axel.notebook.infrastructure.kafka.consumers;

import com.axel.notebook.application.services.IYearConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class YearConsumer implements IYearConsumer {
    private static final ConcurrentMap<String, CompletableFuture<Integer>> pendingRequests = new ConcurrentHashMap<>();

    public CompletableFuture<Integer> createFuture(String token) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        pendingRequests.put(token, future);
        return future;
    }

    @KafkaListener(topics = "profile-response-topic", groupId = "notebook-group")
    public void receiveProfileId(ConsumerRecord<String, String> record) {
        String token = record.key();  // Se espera que el token sea la clave
        Integer profileId = Integer.parseInt(record.value());

        CompletableFuture<Integer> future = pendingRequests.remove(token);
        if (future != null) {
            future.complete(profileId);
        }
    }
}
