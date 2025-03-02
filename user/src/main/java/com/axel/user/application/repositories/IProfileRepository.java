package com.axel.user.application.repositories;

import com.axel.user.application.DTOs.ProfileApplication;

public interface IProfileRepository {
    public ProfileApplication findProfileByIdUser(int idUser);
    public ProfileApplication addProfile(ProfileApplication profile);
}
