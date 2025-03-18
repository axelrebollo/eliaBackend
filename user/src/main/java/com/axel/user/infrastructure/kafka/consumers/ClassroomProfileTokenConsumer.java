package com.axel.user.infrastructure.kafka.consumers;

import com.axel.user.domain.entities.User;
import com.axel.user.infrastructure.exceptions.InfrastructureException;
import com.axel.user.infrastructure.kafka.producers.ClassroomProfileProducer;
import com.axel.user.infrastructure.persistence.JpaProfileRepository;
import com.axel.user.infrastructure.persistence.JpaUserRepository;
import com.axel.user.infrastructure.repositories.UserRepositoryImpl;
import com.axel.user.infrastructure.security.JWTRepositoryImpl;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ClassroomProfileTokenConsumer {
    //Dependency injection
    private final UserRepositoryImpl userRepository;
    private final JWTRepositoryImpl jwtRepository;
    private final JpaProfileRepository jpaProfileRepository;
    private final ClassroomProfileProducer classroomProfileProducer;
    private final JpaUserRepository jpaUserRepository;

    //Constructor
    public ClassroomProfileTokenConsumer(UserRepositoryImpl userRepository,
                                         JWTRepositoryImpl jwtRepository,
                                         JpaProfileRepository jpaProfileRepository,
                                         ClassroomProfileProducer classroomProfileProducer, JpaUserRepository jpaUserRepository) {
        this.userRepository = userRepository;
        this.jwtRepository = jwtRepository;
        this.jpaProfileRepository = jpaProfileRepository;
        this.classroomProfileProducer = classroomProfileProducer;
        this.jpaUserRepository = jpaUserRepository;
    }

    //Listen to topic "petition-idProfile-course", only one consumer from this group processes the message
    @KafkaListener(topics = "petition-data-classroomProfile", groupId = "classroomProfile-group")
    public void processToken(ConsumerRecord<String, String> record){
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
            if (user == null) {
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

        //get role
        String role = jpaUserRepository.findByEmail(email).getRole();

        if(role == null) {
            throw new InfrastructureException("Error con el rol, el usuario no tiene.");
        }

        classroomProfileProducer.sendData(token, idProfile, role, correlationId);
    }
}
