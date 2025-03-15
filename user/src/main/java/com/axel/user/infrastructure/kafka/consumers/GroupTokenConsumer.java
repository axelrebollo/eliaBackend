package com.axel.user.infrastructure.kafka.consumers;

import com.axel.user.domain.entities.User;
import com.axel.user.infrastructure.exceptions.InfrastructureException;
import com.axel.user.infrastructure.kafka.producers.GroupProfileProducer;
import com.axel.user.infrastructure.persistence.JpaProfileRepository;
import com.axel.user.infrastructure.repositories.UserRepositoryImpl;
import com.axel.user.infrastructure.security.JWTRepositoryImpl;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

@Service
public class GroupTokenConsumer {
    //Dependency injection
    private final JWTRepositoryImpl jwtRepository;
    private final JpaProfileRepository jpaProfileRepository;
    private final UserRepositoryImpl userRepository;
    private final GroupProfileProducer groupProfileProducer;

    @Bean
    public KafkaAdmin.NewTopics createGroupTopic() {
        return new KafkaAdmin.NewTopics(
                new NewTopic("petition-idProfile-group", 1, (short)1)
        );
    }

    //Constructor
    public GroupTokenConsumer(JWTRepositoryImpl jwtRepository,
                              JpaProfileRepository jpaProfileRepository,
                              UserRepositoryImpl userRepository,
                              GroupProfileProducer groupProfileProducer){
        this.jwtRepository = jwtRepository;
        this.jpaProfileRepository = jpaProfileRepository;
        this.userRepository = userRepository;
        this.groupProfileProducer = groupProfileProducer;
    }

    //Listen to topic "petition-idProfile-group", only one consumer from this group processes the message
    @KafkaListener(topics = "petition-idProfile-group", groupId = "group-group")
    public void processToken(ConsumerRecord<String, String> record) {
        //extract data to message
        String token = record.value();
        int idProfile = 0;

        //extract id from message
        Headers headers = record.headers();
        Header correlationIdHeader = headers.lastHeader("kafka_correlationId");

        if (correlationIdHeader == null) {
            throw new InfrastructureException("No hay id de correlación en el mensaje recibido");
        }
        String correlationId = new String(correlationIdHeader.value());

        //decrypt token and extract email
        String email = jwtRepository.getEmailFromToken(token);
        if (email == null) {
            throw new InfrastructureException("Token inválido");
        }

        //validate token and extract idUser
        Boolean isAutenticated = jwtRepository.isTokenValid(token, email);
        if (isAutenticated) {
            User user = userRepository.findByEmail(email);
            if(user == null) {
                throw new InfrastructureException("No existe el usuario con el email " + email);
            }
            //get idUser
            int idUser = user.getId();

            //get idProfile
            idProfile = jpaProfileRepository.findByUser_Id(idUser).getId();
        }

        if(idProfile <= 0) {
            throw new InfrastructureException("El perfil no existe");
        }

        //return response with idProfile
        groupProfileProducer.sendProfileId(token, idProfile, correlationId);
    }
}
