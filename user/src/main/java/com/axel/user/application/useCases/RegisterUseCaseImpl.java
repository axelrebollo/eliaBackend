package com.axel.user.application.useCases;

import com.axel.user.application.DTOs.ProfileResponse;
import com.axel.user.application.DTOs.UserApplication;
import com.axel.user.application.DTOs.UserResponse;
import com.axel.user.application.adapters.UserAdapterApplication;
import com.axel.user.application.exceptions.ApplicationException;
import com.axel.user.application.services.IManageProfileUseCase;
import com.axel.user.application.services.IRegisterUserCase;
import com.axel.user.application.repositories.IUserRepository;

import com.axel.user.domain.entities.User;
import com.axel.user.domain.services.interfaces.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterUseCaseImpl implements IRegisterUserCase {

    private final IUserRepository userRepository;
    private final IUserService userService;
    private final UserAdapterApplication userAdapterApplication;
    private final IManageProfileUseCase manageProfileUseCase;

    //contructor
    @Autowired
    public RegisterUseCaseImpl(IUserRepository userRepository,
                               IUserService userService,
                               UserAdapterApplication userAdapterApplication,
                               IManageProfileUseCase manageProfileUseCase) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.userAdapterApplication = userAdapterApplication;
        this.manageProfileUseCase = manageProfileUseCase;
    }

    //Register user or create a new user
    public UserResponse registerUser(final String email, final String password, String role) {
        User userDomain;

        //Check data
        if(email.isEmpty() || password.isEmpty() || role.isEmpty()){
            throw new ApplicationException("El usuario tiene algún campo vacío");
        }

        //create user entity domain and check that user not exists
        if(this.userRepository.findByEmail(email) == null){
            userDomain = userService.createModelUser(email, password, role);
        }
        else{
            throw new ApplicationException("El usuario ya existe en el sistema, por favor intentelo de nuevo.");
        }

        //convert user domain to user application
        UserApplication userApplication = userAdapterApplication.toApplication(
                userDomain.getEmail(), userDomain.getPassword(), userDomain.getRole().toString());

        //Save user into database
        try{
            userApplication = this.userRepository.save(userApplication);
            if(userApplication == null){
                throw new ApplicationException("Error al guardar el usuario en la base de datos, el usuario es nulo");
            }

            //create an empty profile for user
            ProfileResponse profileResponse = manageProfileUseCase.addProfile(userApplication.getId());

            if(profileResponse == null){
                throw new ApplicationException("Error al crear el perfil a partir del usuario.");
            }

            return new UserResponse(userApplication.getEmail(), userApplication.getRole());
        }
        catch(Exception e){
            throw new ApplicationException("Fallo al guardar el usuario en la base de datos", e);
        }
    }
}
