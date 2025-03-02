package com.axel.user.domain.services;

import com.axel.user.domain.entities.User;
import com.axel.user.domain.exceptions.DomainException;
import com.axel.user.domain.services.interfaces.IUserService;
import com.axel.user.domain.valueObjects.Role;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    public UserServiceImpl() {}

    //Create an Entity User
    public User createModelUser(String email, String password, String role){
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

        //encriptPassword
        CriptoService criptoService = new CriptoService();
        String passwordEncripted = criptoService.encrypt(password);
        if(passwordEncripted == null){
            throw new DomainException("Error con la encriptación del password.");
        }
        
        return new User(email, passwordEncripted, roleEnum);
    }

    public String decriptPassword(String password){
        CriptoService criptoService = new CriptoService();
        return criptoService.decrypt(password);
    }

    public boolean isIdenticalPassword(String passwordUser, String passwordDB) {
        return passwordUser.equals(passwordDB);
    }
}
