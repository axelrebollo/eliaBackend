package com.axel.user.application.useCases;

import com.axel.user.domain.entities.Profile;
import com.axel.user.application.DTOs.ProfileResponse;
import com.axel.user.application.exceptions.ApplicationException;
import com.axel.user.application.repositories.IJWTRepository;
import com.axel.user.application.repositories.IProfileRepository;
import com.axel.user.application.repositories.IUserRepository;
import com.axel.user.application.services.IManageProfileUseCase;
import com.axel.user.domain.entities.User;
import com.axel.user.domain.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageProfileUseCaseImpl implements IManageProfileUseCase {
    //Dependency injection
    private final IJWTRepository jwtRepository;   //infrastructure layer
    private final IUserRepository userRepository; //infrastructure layer
    private final IProfileRepository profileRepository;   //infrastructure layer
    private final ProfileService profileService;  //domain layer

    //Constructor
    @Autowired
    public ManageProfileUseCaseImpl(IJWTRepository jwtRepository,
                                    IUserRepository userRepository,
                                    IProfileRepository profileRepository) {
        this.jwtRepository = jwtRepository;
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.profileService = new ProfileService();
    }

    //Get information about user with email
    public ProfileResponse getProfile(String token) {
        //check data
        if(token == null) {
            throw new ApplicationException("Error con la autenticación con el token de acceso.");
        }

        //deserialize token to email
        String email = jwtRepository.getEmailFromToken(token);

        if(email == null) {
            throw new ApplicationException("Error, no existe email en el token de acceso.");
        }

        //Get user into database
        User user = userRepository.findByEmail(email);

        if(user == null) {
            throw new ApplicationException("El usuario no existe");
        }

        //getProfile
        Profile profile = profileRepository.findProfileByIdUser(user.getId());

        if(profile == null) {
            throw new ApplicationException("Error en perfil de usuario. No existe.");
        }

        return new ProfileResponse(profile.getName(), profile.getSurname1(),
                profile.getSurname2());
    }

    //Creates empty profile when user is created
    public ProfileResponse addProfile(int idUser){
        //check data
        if(idUser <= 0) {
            throw new ApplicationException("El usuario no existe en el sistema. Error con el idUser");
        }

        //Create profile
        Profile profile = profileService.addProfile(idUser);

        //Save profile
        Profile profileResponse = profileRepository.save(profile);

        return new ProfileResponse(profileResponse.getName(),
                profileResponse.getSurname1(), profileResponse.getSurname2());
    }

    public ProfileResponse updateProfile(String token, String name, String surname1, String surname2){
        //check data
        if(token == null) {
            throw new ApplicationException("Error con la autenticación con el token de acceso.");
        }

        if(name == null || name.isEmpty() || surname1 == null || surname1.isEmpty() ||
                surname2 == null || surname2.isEmpty()) {
            throw new ApplicationException("Algún dato está vacío o incompleto. (nombre o apellidos");
        }

        //deserialize token to email
        String email = jwtRepository.getEmailFromToken(token);

        //User
        User user = userRepository.findByEmail(email);

        if(user == null) {
            throw new ApplicationException("El usuario no existe");
        }

        //Get profile
        Profile profile = profileRepository.findProfileByIdUser(user.getId());

        //update profile
        if(profile == null) {
            throw new ApplicationException("El profile no existe");
        }
        profile.initializeNotNull(name, surname1, surname2);

        //save profile
        Profile profileResponse = profileRepository.save(profile);

        if(profileResponse == null) {
            throw new ApplicationException("Ha habido un problema al actualizar el perfil del usuario");
        }

        return new ProfileResponse(profileResponse.getName(),
                profileResponse.getSurname1(), profileResponse.getSurname2());
    }
}
