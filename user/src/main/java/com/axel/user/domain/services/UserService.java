package com.axel.user.domain.services;

import com.axel.user.domain.entities.User;
import com.axel.user.domain.repositories.UserRepository;
import com.axel.user.domain.valueObjects.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Check into DB that the user not exists
    public boolean validateUser(String email){
        return userRepository.findByEmail(email) == null;
    }

    //Create an Entity User
    public User createModelUser(String email, String password, Role role){
        return new User(email, password, role);
    }
}
