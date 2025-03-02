package com.axel.user.domain.services.interfaces;

import com.axel.user.domain.entities.User;

public interface IUserService {
    public User createModelUser(String email, String password, String role);
    public String decryptPassword(String password);
    public boolean isIdenticalPassword(String passwordUser, String passwordDB);
}
