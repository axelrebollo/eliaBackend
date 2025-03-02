package com.axel.user.application.repositories;

import com.axel.user.application.DTOs.ProfileApplication;

public interface IProfileRepository {
    //Find profile by idUser
    public ProfileApplication findProfileByIdUser(int idUser);

    //Create profile from ProfileApplication
    public ProfileApplication addProfile(ProfileApplication profile);
}
