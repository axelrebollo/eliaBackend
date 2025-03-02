package com.axel.user.application.adapters;

import com.axel.user.application.DTOs.UserApplication;
import org.springframework.stereotype.Service;

@Service
public class UserAdapterApplication {
    //Constructor
    public UserAdapterApplication(){}

    //Map API to Application
    public UserApplication toApplication(String email, String password, String role) {
        return new UserApplication(email, password, role);
    }
}
