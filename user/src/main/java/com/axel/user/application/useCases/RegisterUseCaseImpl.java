package com.axel.user.application.useCases;

import com.axel.user.application.DTOs.ProfileResponse;
import com.axel.user.application.DTOs.UserResponse;
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

    //Dependency injection
    private final IUserRepository userRepository;
    private final IUserService userService;
    private final IManageProfileUseCase manageProfileUseCase;

    //Constructor
    @Autowired
    public RegisterUseCaseImpl(IUserRepository userRepository,
                               IUserService userService,
                               IManageProfileUseCase manageProfileUseCase) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.manageProfileUseCase = manageProfileUseCase;
    }

    //Register use case
    public UserResponse registerUser(final String email, final String password, String role) {
        //Check data
        if(email.isEmpty() || password.isEmpty() || role.isEmpty()){
            throw new ApplicationException("El usuario tiene algún campo vacío");
        }

        if(this.userRepository.findByEmail(email) != null){
            throw new ApplicationException("El usuario ya existe en el sistema, por favor intentelo de nuevo.");
        }

        User user = userService.createModelUser(email, password, role);

        //Save user into database
        try{
            user = this.userRepository.save(user);
            if(user == null){
                throw new ApplicationException("Error al guardar el usuario en la base de datos, el usuario es nulo");
            }

            //create an empty profile for user
            ProfileResponse profileResponse = manageProfileUseCase.addProfile(user.getId());

            if(profileResponse == null){
                throw new ApplicationException("Error al crear el perfil a partir del usuario.");
            }

            return new UserResponse(user.getEmail(), user.getRole().toString());
        }
        catch(Exception e){
            throw new ApplicationException("Fallo al guardar el usuario en la base de datos", e);
        }
    }
}
