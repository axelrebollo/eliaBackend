package com.axel.user.infrastructure.adapters;

import com.axel.user.domain.entities.User;
import com.axel.user.infrastructure.JpaEntities.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserAdapter {

    public UserAdapter(){}

    public UserEntity UserToUserEntity(User user){
        return new UserEntity(user.getEmail(), user.getPassword(), user.getRole());
    }
}
