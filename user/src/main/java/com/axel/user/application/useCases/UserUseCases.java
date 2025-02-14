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
import org.springframework.stereotype.Service;

@Service
public class UserUseCases {

    private final UserRepository userRepository;
    CriptoService criptoService;
    UserAdapter userAdapter;

    //contructor
    public UserUseCases(UserRepository userRepository) {
        this.userRepository = userRepository;
        criptoService = new CriptoService();
        userAdapter = new UserAdapter();
    }

    public UserResponse registerUser(final String email, final String password, Role role) {
        UserResponse userResponse = null;
        User user = null;
        UserService userService = new UserService();
        UserRepository userRepository = this.userRepository;

        //create user entity domain and check that user not exists
        if(userService.validateUser(email)){
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
            userRepository.save(userEntity);

            userResponse = new UserResponse(user.getEmail(), user.getRole().toString());
        }
        catch(Exception e){
            throw new UserCreationException("Fallo al guardar el usuario en la base de datos");
        }

        return userResponse;
    }

}
