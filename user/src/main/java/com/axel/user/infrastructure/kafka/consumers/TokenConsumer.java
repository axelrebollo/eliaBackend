package com.axel.user.infrastructure.kafka.consumers;

import com.axel.user.application.DTOs.UserApplication;
import com.axel.user.infrastructure.exceptions.InfrastructureException;
import com.axel.user.infrastructure.kafka.producers.ProfileProducer;
import com.axel.user.infrastructure.persistence.JpaProfileRepository;
import com.axel.user.infrastructure.repositories.UserRepositoryImpl;
import com.axel.user.infrastructure.security.JWTRepositoryImpl;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TokenConsumer {
    //Dependency injection
    private final ProfileProducer profileProducer;
    private final UserRepositoryImpl userRepository;
    private final JWTRepositoryImpl jwtRepository;
    private final JpaProfileRepository jpaProfileRepository;

    //Constructor
    public TokenConsumer(ProfileProducer profileProducer,
                         UserRepositoryImpl userRepository,
                         JWTRepositoryImpl jwtRepository,
                         JpaProfileRepository jpaProfileRepository) {
        this.profileProducer = profileProducer;
        this.userRepository = userRepository;
        this.jwtRepository = jwtRepository;
        this.jpaProfileRepository = jpaProfileRepository;
    }

    //arrives token and extract info
    @KafkaListener(topics = "token-topic", groupId = "user-group")
    public void processToken(ConsumerRecord<String, String> record) {
        String token = record.value();
        int idProfile = 0;

        //decript token and extract email
        String email = jwtRepository.getEmailFromToken(token);
        if (email == null) {
            throw new InfrastructureException("Token inválido");
        }

        //check token and extract idUser
        Boolean isAutenticated = jwtRepository.isTokenValid(token, email);
        if (isAutenticated) {
            UserApplication user = userRepository.findByEmail(email);
            if(user == null) {
                throw new InfrastructureException("No existe el usuario con el email " + email);
            }
            int idUser = user.getId();

            //save idProfile
            idProfile = jpaProfileRepository.findByUser_Id(idUser).getId();
        }

        if(idProfile <= 0) {
            throw new InfrastructureException("El perfil no existe");
        }

        //return response
        profileProducer.sendProfileId(token, idProfile);
    }
}
