package com.axel.user.infrastructure.kafka.consumers;

import com.axel.user.domain.entities.User;
import com.axel.user.infrastructure.exceptions.InfrastructureException;
import com.axel.user.infrastructure.kafka.producers.TableProfileProducer;
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
public class TableTokenConsumer {
    //Dependency injection
    private final TableProfileProducer tableProfileProducer;
    private final UserRepositoryImpl userRepository;
    private final JWTRepositoryImpl jwtRepository;
    private final JpaProfileRepository jpaProfileRepository;

    @Bean
    public KafkaAdmin.NewTopics createTableTopic() {
        return new KafkaAdmin.NewTopics(
                new NewTopic("petition-idProfile-table", 1, (short)1)
        );
    }

    //Constructor
    public TableTokenConsumer(TableProfileProducer tableProfileProducer,
                              UserRepositoryImpl userRepository,
                              JWTRepositoryImpl jwtRepository,
                              JpaProfileRepository jpaProfileRepository) {
        this.tableProfileProducer = tableProfileProducer;
        this.userRepository = userRepository;
        this.jwtRepository = jwtRepository;
        this.jpaProfileRepository = jpaProfileRepository;
    }

    //Listen to topic "petition-idProfile-table", only one consumer from this group processes the message
    @KafkaListener(topics = "petition-idProfile-table", groupId = "table-group")
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
        try{
            String email = jwtRepository.getEmailFromToken(token);

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
            tableProfileProducer.sendProfileId(token, idProfile, correlationId);
        }
        catch(Exception e){
            tableProfileProducer.sendError(correlationId, "Token inválido");
        }
    }
}
