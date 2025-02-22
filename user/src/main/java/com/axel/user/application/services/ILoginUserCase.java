package com.axel.user.application.services;

import com.axel.user.application.DTOs.UserResponseToken;

public interface ILoginUserCase {
    public UserResponseToken loginUser(String email, String password);
}
