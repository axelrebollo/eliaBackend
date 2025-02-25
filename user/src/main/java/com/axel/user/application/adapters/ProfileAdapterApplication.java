package com.axel.user.application.adapters;

import com.axel.user.application.DTOs.ProfileApplication;

public class ProfileAdapterApplication {

    public ProfileAdapterApplication() {}

    //Map API to Application
    public ProfileApplication toApplication(int idUser, String name, String surname1, String surname2){
        return new ProfileApplication(idUser, name, surname1, surname2);
    }
}
