package com.axel.user.application.services;

import com.axel.user.application.DTOs.ProfileResponse;

public interface IManageProfileUseCase {
    public ProfileResponse getProfile(String token);
    public ProfileResponse addProfile(int idUser);
}
