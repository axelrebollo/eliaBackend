package com.axel.user.application.repositories;

import com.axel.user.domain.entities.User;

public interface IUserRepository {
    //Save userApplication
    User save(User user);

    //Find by email userApplication
    User findByEmail(String email);

    //Find by idUser UserApplication
    User findByIdUser(int idUser);
}
