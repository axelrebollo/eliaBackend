package com.axel.user.domain.services;

import com.axel.user.domain.entities.User;
import com.axel.user.domain.exceptions.DomainException;
import com.axel.user.domain.valueObjects.Role;

public class UserService{

    //Constructor
    public UserService() {}

    //Create an Entity User
    public User createUser(String email, String password, String role){
        //Convert string role into enum from domain
        Role roleEnum;
        if(role.equals("TEACHER")){
            roleEnum = Role.TEACHER;
        }
        else if(role.equals("STUDENT")){
            roleEnum = Role.STUDENT;
        }
        else{
            throw new DomainException("Error con el rol del usuario.");
        }

        //Crypt Password
        CriptoService criptoService = new CriptoService();
        String passwordEncripted = criptoService.encrypt(password);
        if(passwordEncripted == null){
            throw new DomainException("Error con la encriptación del password.");
        }
        
        return new User(email, passwordEncripted, roleEnum);
    }

    //Decrypt password
    public String decryptPassword(String password){
        CriptoService criptoService = new CriptoService();
        return criptoService.decrypt(password);
    }

    //Compare passwords and return bool
    public boolean isIdenticalPassword(String passwordUser, String passwordDB) {
        return passwordUser.equals(passwordDB);
    }
}
