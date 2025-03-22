package com.axel.notebook.infrastructure.kafka.consumers;

import com.axel.notebook.application.services.consumers.ICellConsumer;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class CellConsumer implements ICellConsumer {
    //Map to save pending requests
    private static final ConcurrentMap<String, CompletableFuture<Map<String, String>>> pendingRequests = new ConcurrentHashMap<>();

    @Bean
    public KafkaAdmin.NewTopics createCellTopics() {
        return new KafkaAdmin.NewTopics(
                new NewTopic("cell", 1, (short) 1)
        );
    }

    //create a response to process and sent after
    public CompletableFuture<Map<String, String>> createFuture(String correlationId) {
        CompletableFuture<Map<String, String>> future = new CompletableFuture<>();
        pendingRequests.put(correlationId, future);
        return future;
    }

    //Listen to "response-data-cell", only one instance from user is processed
    @KafkaListener(topics = "response-data-cell", groupId = "cell-group-consumer")
    public void receiveProfileId(ConsumerRecord<String, String> record) {
        //extract headers
        Headers headers = record.headers();
        Header correlationIdHeader = headers.lastHeader("kafka_correlationId");

        if (correlationIdHeader == null) {
            throw new IllegalStateException("No hay id de correlacion en el mensaje de respuesta");
        }
        String correlationId = new String(correlationIdHeader.value());

        //extract data
        String message = record.value();

        Map<String, String> dataProfile = new HashMap<>();
        try {
            dataProfile = parseJsonToMap(message);
        } catch (JsonProcessingException e) {
            throw new InfrastructureException("Error al procesar el mensaje JSON.");
        }

        //delete pending request and finalize process
        CompletableFuture<Map<String, String>> future = pendingRequests.remove(correlationId);
        //check if request exist
        if (future != null) {
            future.complete(dataProfile);
        }
    }

    //Listen to "response-data-cell", only one instance from user is processed
    @KafkaListener(topics = "response-profile-cell", groupId = "cellProfile-group-consumer")
    public void receiveProfileIdWithoutToken(ConsumerRecord<String, String> record) {
        //extract headers
        Headers headers = record.headers();
        Header correlationIdHeader = headers.lastHeader("kafka_correlationId");

        if (correlationIdHeader == null) {
            throw new IllegalStateException("No hay id de correlacion en el mensaje de respuesta");
        }
        String correlationId = new String(correlationIdHeader.value());

        //extract data
        String message = record.value();

        Map<String, String> dataProfile = new HashMap<>();
        try {
            dataProfile = parseJsonToMap(message);
        } catch (JsonProcessingException e) {
            throw new InfrastructureException("Error al procesar el mensaje JSON.");
        }

        //delete pending request and finalize process
        CompletableFuture<Map<String, String>> future = pendingRequests.remove(correlationId);
        //check if request exist
        if (future != null) {
            future.complete(dataProfile);
        }
    }

    private Map<String, String> parseJsonToMap(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, new TypeReference<Map<String, String>>() {
        });
    }
}

