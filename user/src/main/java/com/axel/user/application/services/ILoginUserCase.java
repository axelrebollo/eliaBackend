package com.axel.user.application.services;

import com.axel.user.application.DTOs.UserResponseToken;

public interface ILoginUserCase {
    //Login use case
    public UserResponseToken loginUser(String email, String password);
}
