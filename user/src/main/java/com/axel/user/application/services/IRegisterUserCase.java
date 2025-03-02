package com.axel.user.application.services;

import com.axel.user.application.DTOs.UserResponse;

public interface IRegisterUserCase {
    //Register use case
    public UserResponse registerUser(String email, String password, String role);
}
