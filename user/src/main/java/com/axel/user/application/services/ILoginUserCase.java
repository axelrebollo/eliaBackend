package com.axel.user.application.services;

import com.axel.user.application.DTOs.UserResponse;

public interface ILoginUserCase {
    public UserResponse loginUser(String email, String password);
}
