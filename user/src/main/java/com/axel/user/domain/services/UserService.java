package com.axel.user.domain.services;

import com.axel.user.domain.entities.User;
import com.axel.user.domain.repositories.UserRepository;
import com.axel.user.domain.valueObjects.Role;
import com.axel.user.infrastructure.JpaEntities.UserEntity;

public class UserService {
    private UserRepository userRepository;

    //Check into DB that the user not exists
    public boolean validateUser(String email){
        boolean result = false;
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity != null){
            result = true;
        }
        return result;
    }

    //Create an Entity User
    public User createModelUser(String email, String password, Role role){
        return new User(email, password, role);
    }
}
