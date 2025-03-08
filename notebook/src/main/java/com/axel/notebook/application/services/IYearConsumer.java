package com.axel.notebook.application.services;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.concurrent.CompletableFuture;

public interface IYearConsumer {
    public void receiveProfileId(ConsumerRecord<String, String> record);

    public CompletableFuture<Integer> createFuture(String token);
}
