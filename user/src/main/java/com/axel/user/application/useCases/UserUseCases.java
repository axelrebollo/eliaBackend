package com.axel.user.application.useCases;

import com.axel.user.application.DTOs.UserResponse;
import com.axel.user.domain.entities.User;
import com.axel.user.domain.exceptions.UserCreationException;
import com.axel.user.domain.repositories.UserRepository;
import com.axel.user.domain.services.CriptoService;
import com.axel.user.domain.services.UserService;
import com.axel.user.domain.valueObjects.Role;
import com.axel.user.infrastructure.JpaEntities.UserEntity;
import com.axel.user.infrastructure.adapters.UserAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUseCases {

    private final UserRepository userRepository;
    private final CriptoService criptoService;
    private final UserAdapter userAdapter;
    private final UserService userService;

    //contructor
    @Autowired
    public UserUseCases(UserRepository userRepository, CriptoService criptoService, UserAdapter userAdapter, UserService userService) {
        this.userRepository = userRepository;
        this.criptoService = criptoService;
        this.userAdapter = userAdapter;
        this.userService = userService;
    }

    public UserResponse registerUser(final String email, final String password, Role role) {
        User user;

        //create user entity domain and check that user not exists
        if(this.userService.validateUser(email)){
            user = userService.createModelUser(email, password, role);
        }
        else{
            throw new UserCreationException("El usuario ya existe en el sistema, por favor intentelo de nuevo.");
        }

        //Encript password
        String encryptedPassword = criptoService.encrypt(password);
        user.setPassword(encryptedPassword);

        //Save user into database
        try{
            UserEntity userEntity = userAdapter.UserToUserEntity(user);
            this.userRepository.save(userEntity);

            return new UserResponse(user.getEmail(), user.getRole().toString());
        }
        catch(Exception e){
            throw new UserCreationException("Fallo al guardar el usuario en la base de datos");
        }
    }
}
