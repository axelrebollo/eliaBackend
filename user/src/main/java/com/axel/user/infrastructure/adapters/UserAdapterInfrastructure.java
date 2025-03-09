package com.axel.user.infrastructure.adapters;

import com.axel.user.domain.entities.User;
import com.axel.user.domain.valueObjects.Role;
import com.axel.user.infrastructure.JpaEntities.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserAdapterInfrastructure {

    //Constructor
    public UserAdapterInfrastructure(){}

    //Map application to infrastructure (id default)
    public UserEntity fromApplication(User user) {
        if(user == null){
            return null;
        }
        return new UserEntity(user.getEmail(), user.getPassword(), user.getRole().toString());
    }

    //Map application to infrastructure with id
    public UserEntity fromApplicationWithIdUser(User user) {
        if(user == null){
            return null;
        }
        return new UserEntity(user.getId(), user.getEmail(), user.getPassword(), user.getRole().toString());
    }

    //Map infrastructure to application
    public User toApplication(UserEntity userEntity) {
        if(userEntity == null){
            return null;
        }
        return new User(userEntity.getId(), userEntity.getEmail(), userEntity.getPassword(), stringToRole(userEntity.getRole()));
    }

    private Role stringToRole(String roleStr){
        return Role.valueOf(roleStr);
    }
}