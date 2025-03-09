package com.axel.user.application.adapters;

import com.axel.user.domain.entities.Profile;
import org.springframework.stereotype.Service;

@Service
public class ProfileAdapterApplication {
    //Constructor
    public ProfileAdapterApplication() {}

    //Map API to Application
    public Profile toApplication(int idUser, String name, String surname1, String surname2){
        return new Profile(idUser, name, surname1, surname2);
    }
}
