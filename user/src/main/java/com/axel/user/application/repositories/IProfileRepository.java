package com.axel.user.application.repositories;

import com.axel.user.domain.entities.Profile;

public interface IProfileRepository {
    //Find profile by idUser
    public Profile findProfileByIdUser(int idUser);

    //Create profile from ProfileApplication
    public Profile save(Profile profile);
}
