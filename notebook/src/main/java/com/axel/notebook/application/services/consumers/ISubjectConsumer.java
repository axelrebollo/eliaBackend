package com.axel.notebook.application.services.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import java.util.concurrent.CompletableFuture;

public interface ISubjectConsumer {
    public void receiveProfileId(ConsumerRecord<String, String> record);

    public CompletableFuture<Integer> createFuture(String token);
}
