package com.axel.user.application.useCases;

import com.axel.user.application.DTOs.UserApplication;
import com.axel.user.application.DTOs.UserResponse;
import com.axel.user.application.exceptions.ApplicationException;
import com.axel.user.application.repositories.IUserRepository;
import com.axel.user.application.services.ILoginUserCase;

import com.axel.user.domain.services.interfaces.IUserService;

import org.springframework.stereotype.Service;

@Service
public class LoginUseCaseImpl implements ILoginUserCase {

    private final IUserRepository userRepository;
    private final IUserService userService;

    public LoginUseCaseImpl(IUserRepository userRepository, IUserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public UserResponse loginUser(final String email, final String password) {
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

        if(isPasswordCorrect){
            //generate token
        }
        else{
            throw new ApplicationException("La contraseña no es correcta");
        }

        //return userResponse + token
        return new UserResponse(userApplication.getEmail(), userApplication.getRole());
    }
}
