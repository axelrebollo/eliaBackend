package com.axel.user.application.services;

import com.axel.user.application.DTOs.ProfileResponse;

public interface IManageProfileUseCase {
    //Get information about user with email
    public ProfileResponse getProfile(String token);

    //Creates empty profile when user is created
    public ProfileResponse addProfile(int idUser);

    //Update data from profile
    public ProfileResponse updateProfile(String token, String name, String surname1, String surname2);
}
