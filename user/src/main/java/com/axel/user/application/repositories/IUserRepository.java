package com.axel.user.application.repositories;

import com.axel.user.application.DTOs.UserApplication;

public interface IUserRepository {
    //Save userApplication
    UserApplication save(UserApplication user);

    //Find by email userApplication
    UserApplication findByEmail(String email);

    //Find by idUser UserApplication
    UserApplication findByIdUser(int idUser);
}
