package com.axel.user.application.repositories;

import com.axel.user.application.DTOs.UserApplication;

public interface IUserRepository {
    UserApplication save(UserApplication user);
    UserApplication findByEmail(String email);
    UserApplication findByIdUser(int idUser);
}
