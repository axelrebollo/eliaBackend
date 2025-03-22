package com.axel.user.infrastructure.kafka.consumers;

import com.axel.user.domain.entities.User;
import com.axel.user.infrastructure.JpaEntities.ProfileEntity;
import com.axel.user.infrastructure.exceptions.InfrastructureException;
import com.axel.user.infrastructure.kafka.producers.CellProfileProducer;
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
public class CellTokenConsumer {
    //Dependency injection
    private final UserRepositoryImpl userRepository;
    private final JWTRepositoryImpl jwtRepository;
    private final JpaProfileRepository jpaProfileRepository;
    private final CellProfileProducer cellProfileProducer;
    private final JpaUserRepository jpaUserRepository;

    //Constructor
    public CellTokenConsumer(UserRepositoryImpl userRepository,
                             JWTRepositoryImpl jwtRepository,
                             JpaProfileRepository jpaProfileRepository,
                             CellProfileProducer cellProfileProducer,
                             JpaUserRepository jpaUserRepository) {
        this.userRepository = userRepository;
        this.jwtRepository = jwtRepository;
        this.jpaProfileRepository = jpaProfileRepository;
        this.cellProfileProducer = cellProfileProducer;
        this.jpaUserRepository = jpaUserRepository;
    }

    //Listen to topic "petition-idProfile-cell"
    @KafkaListener(topics = "petition-data-cell", groupId = "cell-group")
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
            ProfileEntity profile = jpaProfileRepository.findByUser_Id(idUser);

            if(profile == null) {
                throw new InfrastructureException("El perfil no existe");
            }

            //get role
            String role = jpaUserRepository.findByEmail(email).getRole();

            if(role == null) {
                throw new InfrastructureException("Error con el rol, el usuario no tiene.");
            }

            cellProfileProducer.sendData(token, profile, role, correlationId);
        }
    }

    //Listen to topic "petition-idProfile-cell"
    @KafkaListener(topics = "petition-profile-cell", groupId = "cellProfile-group")
    public void processIdProfile(ConsumerRecord<String, String> record){
        //extract data to message
        String idProfileString = record.value();
        int idProfile = Integer.parseInt(idProfileString);

        //extract id from message
        Headers headers = record.headers();
        Header correlationIdHeader = headers.lastHeader("kafka_correlationId");

        if (correlationIdHeader == null) {
            throw new InfrastructureException("No hay id de correlación en el mensaje recibido");
        }
        String correlationId = new String(correlationIdHeader.value());

        if(idProfile <= 0) {
            cellProfileProducer.sendProfile(null, correlationId);
        }

        //get idProfile
        ProfileEntity profile = jpaProfileRepository.findByIdProfile(idProfile);

        if(profile == null) {
            throw new InfrastructureException("El perfil no existe");
        }

        cellProfileProducer.sendProfile(profile, correlationId);
    }
}
