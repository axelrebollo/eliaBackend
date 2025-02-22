package com.axel.user.application.useCases;

import com.axel.user.application.DTOs.UserApplication;
import com.axel.user.application.DTOs.UserResponseToken;
import com.axel.user.application.exceptions.ApplicationException;
import com.axel.user.application.repositories.IJWTRepository;
import com.axel.user.application.repositories.IUserRepository;
import com.axel.user.application.services.ILoginUserCase;

import com.axel.user.domain.services.interfaces.IUserService;

import org.springframework.stereotype.Service;

@Service
public class LoginUseCaseImpl implements ILoginUserCase {

    private final IUserRepository userRepository;
    private final IUserService userService;
    private final IJWTRepository jwtRepository;

    public LoginUseCaseImpl(IUserRepository userRepository, IUserService userService, IJWTRepository jwtRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtRepository = jwtRepository;
    }

    public UserResponseToken loginUser(final String email, final String password) {
        //check the data imported
        if(email == null || password == null) {
            throw new ApplicationException("El usuario tiene algún campo vacío");
        }

        //get info user credentials
        UserApplication userApplication = userRepository.findByEmail(email);

        if(userApplication == null) {
            throw new ApplicationException("El usuario no existe");
        }

        //decript password
        String decriptedPassword = userService.decriptPassword(userApplication.getPassword());
        userApplication.setPassword(decriptedPassword);

        //check passwords
        boolean isPasswordCorrect = userService.isIdenticalPassword(userApplication.getPassword(), password);
        String token;

        if(isPasswordCorrect){
            //generate token
            token = jwtRepository.generateToken(userApplication.getEmail(), userApplication.getRole());
        }
        else{
            throw new ApplicationException("La contraseña no es correcta");
        }

        //return userResponse + token
        return new UserResponseToken(userApplication.getEmail(), userApplication.getRole(), token);
    }
}
