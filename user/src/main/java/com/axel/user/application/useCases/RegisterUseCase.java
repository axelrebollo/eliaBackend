package com.axel.user.application.useCases;

import com.axel.user.application.DTOs.UserApplication;
import com.axel.user.application.DTOs.UserResponse;
import com.axel.user.application.adapters.UserAdapterApplication;
import com.axel.user.application.repositories.UserRepository;

import com.axel.user.domain.entities.User;
import com.axel.user.domain.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axel.user.domain.exceptions.UserCreationException;

@Service
public class RegisterUseCase {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserAdapterApplication userAdapterApplication;

    //contructor
    @Autowired
    public RegisterUseCase(UserRepository userRepository, UserService userService,
                           UserAdapterApplication userAdapterApplication){
        this.userRepository = userRepository;
        this.userService = userService;
        this.userAdapterApplication = userAdapterApplication;
    }

    //Register user or create a new user
    public UserResponse registerUser(final String email, final String password, String role) {
        User userDomain;

        //Check data
        if(email.isEmpty() || password.isEmpty() || role.isEmpty()){
            throw new UserCreationException("El usuario tiene algún campo vacío");
        }

        //create user entity domain and check that user not exists
        if(this.userRepository.findByEmail(email) == null){
            userDomain = userService.createModelUser(email, password, role);
        }
        else{
            throw new UserCreationException("El usuario ya existe en el sistema, por favor intentelo de nuevo.");
        }

        //convert user domain to user application
        UserApplication userApplication = userAdapterApplication.toApplication(userDomain.getEmail(), userDomain.getPassword(), userDomain.getRole().toString());

        //Save user into database
        try{
            userApplication = this.userRepository.save(userApplication);
            if(userApplication == null){
                throw new UserCreationException("El guardado del usuario es null, fallo en el guardado");
            }
            return new UserResponse(userApplication.getEmail(), userApplication.getRole());
        }
        catch(Exception e){
            throw new UserCreationException("Fallo al guardar el usuario en la base de datos");
        }
    }
}
