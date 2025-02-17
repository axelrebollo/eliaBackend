package com.axel.user.domain.services;

import com.axel.user.domain.entities.User;
import com.axel.user.domain.valueObjects.Role;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public UserService() {}

    //Create an Entity User
    public User createModelUser(String email, String password, String role){
        //Convert string role into enum from domain
        Role roleEnum;
        if(role.equals("TEACHER")){
            roleEnum = Role.TEACHER;
        }
        else{
            roleEnum = Role.STUDENT;
        }

        //encriptPassword
        CriptoService criptoService = new CriptoService();
        String passwordEncripted = criptoService.encrypt(password);

        return new User(email, passwordEncripted, roleEnum);
    }
}
