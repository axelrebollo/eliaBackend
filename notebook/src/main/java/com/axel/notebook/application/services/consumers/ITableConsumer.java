package com.axel.notebook.application.services.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.concurrent.CompletableFuture;

public interface ITableConsumer {
    //create a response to process and sent after
    public CompletableFuture<Integer> createFuture(String correlationId);

    //Listen to "response-idProfile-table", only one instance from group is processed
    public void receiveProfileId(ConsumerRecord<String, String> record);
}
