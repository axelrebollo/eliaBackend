package com.axel.notebook.infrastructure.kafka.consumers;

import com.axel.notebook.application.services.consumers.ICourseConsumer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class CourseConsumer implements ICourseConsumer {
    //Map to save pending requests
    private static final ConcurrentMap<String, CompletableFuture<Integer>> pendingRequests = new ConcurrentHashMap<>();

    @Bean
    public KafkaAdmin.NewTopics createCourseTopics() {
        return new KafkaAdmin.NewTopics(
                new NewTopic("courses", 1, (short) 1)
        );
    }

    //create a response to process and sent after
    public CompletableFuture<Integer> createFuture(String correlationId) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        pendingRequests.put(correlationId, future);
        return future;
    }

    //Listen to "response-idProfile-course", only one instance from group is processed
    @KafkaListener(topics = "response-idProfile-course", groupId = "course-group-consumer")
    public void receiveProfileId(ConsumerRecord<String, String> record) {
        //extract headers
        Headers headers = record.headers();
        Header correlationIdHeader = headers.lastHeader("kafka_correlationId");

        if (correlationIdHeader == null) {
            throw new IllegalStateException("No hay id de correlacion en el mensaje de respuesta");
        }
        String correlationId = new String(correlationIdHeader.value());
        String value = record.value();

        //delete pending request and finalize process
        CompletableFuture<Integer> future = pendingRequests.remove(correlationId);
        if (future == null) {
            return;
        }

        try{
            //check if is a JSON error
            if (value.contains("\"status\"") && value.contains("\"error\"")) {
                String message = extractMessageFromJson(value);
                future.completeExceptionally(new RuntimeException("Error del microservicio user: " + message));
            } else {
                //parse profile id
                Integer profileId = Integer.parseInt(value);
                future.complete(profileId);
            }
        }
        catch (Exception e){
            future.completeExceptionally(new RuntimeException("Error al procesar la respuesta", e));
        }
    }

    private String extractMessageFromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = mapper.readValue(json, Map.class);
            return map.getOrDefault("message", "Error desconocido");
        } catch (Exception e) {
            return "Error desconocido (no se pudo parsear)";
        }
    }
}