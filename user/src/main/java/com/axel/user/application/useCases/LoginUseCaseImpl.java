package com.axel.user.application.useCases;

import com.axel.user.application.DTOs.UserResponseToken;
import com.axel.user.application.exceptions.ApplicationException;
import com.axel.user.application.repositories.IJWTRepository;
import com.axel.user.application.repositories.IUserRepository;
import com.axel.user.application.services.ILoginUserCase;
import com.axel.user.domain.entities.User;
import com.axel.user.domain.services.interfaces.IUserService;
import org.springframework.stereotype.Service;

@Service
public class LoginUseCaseImpl implements ILoginUserCase {

    //Dependency injection
    private final IUserRepository userRepository;
    private final IUserService userService;
    private final IJWTRepository jwtRepository;

    //Constructor
    public LoginUseCaseImpl(IUserRepository userRepository, IUserService userService, IJWTRepository jwtRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtRepository = jwtRepository;
    }

    //Login user case
    public UserResponseToken loginUser(final String email, final String password) {
        //check the data imported
        if(email == null || password == null) {
            throw new ApplicationException("El usuario tiene algún campo vacío");
        }

        //get info user credentials
        User user = userRepository.findByEmail(email);

        if(user == null) {
            throw new ApplicationException("El usuario no existe");
        }

        //decrypt password
        String decryptedPassword = userService.decryptPassword(user.getPassword());
        user.setPassword(decryptedPassword);

        //check passwords
        boolean isPasswordCorrect = userService.isIdenticalPassword(user.getPassword(), password);
        String token;

        if(isPasswordCorrect){
            //generate token
            token = jwtRepository.generateToken(user.getEmail(), user.getRole().toString());
        }
        else{
            throw new ApplicationException("La contraseña no es correcta");
        }

        //return userResponse + token
        return new UserResponseToken(token);
    }
}
