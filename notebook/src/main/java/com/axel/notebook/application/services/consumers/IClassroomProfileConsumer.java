package com.axel.notebook.application.services.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface IClassroomProfileConsumer {
    public void receiveProfileId(ConsumerRecord<String, String> record);

    public CompletableFuture<Map<String, String>> createFuture(String correlationId);
}
