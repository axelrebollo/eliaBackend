package com.axel.user.application.repositories;

import com.axel.user.application.DTOs.UserApplication;

public interface UserRepository {
    UserApplication save(UserApplication user);
    UserApplication findByEmail(String email);
}
