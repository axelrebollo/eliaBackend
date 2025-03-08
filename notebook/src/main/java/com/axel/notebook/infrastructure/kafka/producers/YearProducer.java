package com.axel.notebook.infrastructure.kafka.producers;

import com.axel.notebook.application.services.IYearProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class YearProducer implements IYearProducer {
    private final ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;

    public YearProducer(ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate) {
        this.replyingKafkaTemplate = replyingKafkaTemplate;
    }

    public int sendToken(String token) {
        String requestTopic = "token-topic";
        String replyTopic = "profile-response-topic";

        ProducerRecord<String, String> record = new ProducerRecord<>(requestTopic, token);
        record.headers().add(new RecordHeader("reply-topic", replyTopic.getBytes()));

        RequestReplyFuture<String, String, String> future = replyingKafkaTemplate.sendAndReceive(record);

        try {
            String response = future.get(10, TimeUnit.SECONDS).value(); // Espera la respuesta
            return Integer.parseInt(response); // Convierte la respuesta en Integer
        } catch (Exception e) {
            throw new RuntimeException("Error al recibir respuesta: " + e.getMessage());
        }
    }
}
