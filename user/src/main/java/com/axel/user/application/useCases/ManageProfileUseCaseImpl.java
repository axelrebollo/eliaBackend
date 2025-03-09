package com.axel.user.application.useCases;

import com.axel.user.application.DTOs.ProfileApplication;
import com.axel.user.application.DTOs.ProfileResponse;
import com.axel.user.application.exceptions.ApplicationException;
import com.axel.user.application.repositories.IJWTRepository;
import com.axel.user.application.repositories.IProfileRepository;
import com.axel.user.application.repositories.IUserRepository;
import com.axel.user.application.services.IManageProfileUseCase;
import com.axel.user.domain.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageProfileUseCaseImpl implements IManageProfileUseCase {

    //Dependency injection
    private IJWTRepository jwtRepository;
    private IUserRepository userRepository;
    private IProfileRepository profileRepository;

    //Constructor
    @Autowired
    public ManageProfileUseCaseImpl(IJWTRepository jwtRepository,
                                    IUserRepository userRepository,
                                    IProfileRepository profileRepository) {
        this.jwtRepository = jwtRepository;
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    //Get information about user with email
    public ProfileResponse getProfile(String token) {
        if(token == null) {
            throw new ApplicationException("Error con la autenticación con el token de acceso.");
        }

        //deserialize token to email
        String email = jwtRepository.getEmailFromToken(token);

        if(email == null) {
            throw new ApplicationException("Error, no existe email en el token de acceso.");
        }

        if(!jwtRepository.isTokenValid(token, email)) {
            throw new ApplicationException("Token inválido");
        }

        //User
        User user = userRepository.findByEmail(email);

        if(user == null) {
            throw new ApplicationException("El usuario no existe");
        }

        //getProfile
        ProfileApplication profileApplication = profileRepository.findProfileByIdUser(user.getId());

        if(profileApplication == null) {
            throw new ApplicationException("Error en perfil de usuario. No existe.");
        }

        return new ProfileResponse(profileApplication.getName(), profileApplication.getSurname1(),
                profileApplication.getSurname2());
    }

    //Creates empty profile when user is created
    public ProfileResponse addProfile(int idUser){
        if(idUser <= 0) {
            throw new ApplicationException("El usuario no existe en el sistema. Error con el idUser");
        }

        ProfileApplication profileApplication = new ProfileApplication(idUser, "-", "-","-");

        //create profile
        ProfileApplication profileApplicationResponse = profileRepository.save(profileApplication);

        return new ProfileResponse(profileApplicationResponse.getName(),
                profileApplicationResponse.getSurname1(), profileApplicationResponse.getSurname2());
    }

    public ProfileResponse updateProfile(String token, String name, String surname1, String surname2){
        if(token == null) {
            throw new ApplicationException("Error con la autenticación con el token de acceso.");
        }

        //deserialize token to email
        String email;
        email = jwtRepository.getEmailFromToken(token);

        if(email == null) {
            throw new ApplicationException("Error, no existe email en el token de acceso o el token es incorrecto.");
        }

        if(!jwtRepository.isTokenValid(token, email)) {
            throw new ApplicationException("Token inválido");
        }

        if(name == null || name.isEmpty() || surname1 == null || surname1.isEmpty() ||
                surname2 == null || surname2.isEmpty()) {
            throw new ApplicationException("Algún dato está vacío o incompleto. (nombre o apellidos");
        }

        //User
        User user = userRepository.findByEmail(email);

        if(user == null) {
            throw new ApplicationException("El usuario no existe");
        }

        //Update profile
        ProfileApplication profileApplication = profileRepository.findProfileByIdUser(user.getId());
        profileApplication.setName(name);
        profileApplication.setSurname1(surname1);
        profileApplication.setSurname2(surname2);
        ProfileApplication profileApplicationResponse = profileRepository.save(profileApplication);

        if(profileApplicationResponse == null) {
            throw new ApplicationException("Ha habido un problema al actualizar el perfil del usuario");
        }

        return new ProfileResponse(profileApplicationResponse.getName(),
                profileApplicationResponse.getSurname1(), profileApplicationResponse.getSurname2());
    }
}
