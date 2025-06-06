package com.axel.notebook.application.services.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.concurrent.CompletableFuture;

public interface ICourseConsumer {
    //Listen to "response-idProfile-course", only one instance from group is processed
    public void receiveProfileId(ConsumerRecord<String, String> record);

    //create a response to process and sent after
    public CompletableFuture<Integer> createFuture(String correlationId);
}
