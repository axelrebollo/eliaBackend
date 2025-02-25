package com.axel.user.application.useCases;

import com.axel.user.application.DTOs.ProfileApplication;
import com.axel.user.application.DTOs.ProfileResponse;
import com.axel.user.application.DTOs.UserApplication;
import com.axel.user.application.exceptions.ApplicationException;
import com.axel.user.application.repositories.IJWTRepository;
import com.axel.user.application.repositories.IProfileRepository;
import com.axel.user.application.repositories.IUserRepository;

import com.axel.user.application.services.IManageProfileUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageProfileUseCaseImpl implements IManageProfileUseCase {

    private IJWTRepository jwtRepository;
    private IUserRepository userRepository;
    private IProfileRepository profileRepository;

    @Autowired
    public ManageProfileUseCaseImpl(IJWTRepository jwtRepository, IUserRepository userRepository,
                                    IProfileRepository profileRepository) {
        this.jwtRepository = jwtRepository;
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    //Get information about user with email
    public ProfileResponse getProfile(String token) {
        //deserialize token to email
        String email = jwtRepository.getEmailFromToken(token);

        if(!jwtRepository.isTokenValid(token, email)) {
            throw new ApplicationException("Token inválido");
        }

        //User
        UserApplication user = userRepository.findByEmail(email);

        //getProfile
        ProfileApplication profileApplication = profileRepository.findProfileByIdUser(user.getId());

        return new ProfileResponse(profileApplication.getName(), profileApplication.getSurname1(),
                profileApplication.getSurname2());
    }
}
