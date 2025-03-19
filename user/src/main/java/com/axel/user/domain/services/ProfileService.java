package com.axel.user.domain.services;

import com.axel.user.domain.entities.Profile;
import com.axel.user.domain.exceptions.DomainException;

public class ProfileService {
    //Constructor
    public ProfileService() {}

    public Profile addProfile(int idUser){
        if(idUser<=0){
            throw new DomainException("El id de usuario no es correcto.");
        }
        Profile profile = new Profile();
        profile.setName("-");
        profile.setSurname1("-");
        profile.setSurname2("-");
        profile.setIdUser(idUser);
        return profile;
    }
}
