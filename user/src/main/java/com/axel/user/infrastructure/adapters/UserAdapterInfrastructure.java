package com.axel.user.infrastructure.adapters;

import com.axel.user.application.DTOs.UserApplication;

import com.axel.user.infrastructure.JpaEntities.UserEntity;

import org.springframework.stereotype.Service;

@Service
public class UserAdapterInfrastructure {

    public UserAdapterInfrastructure(){}

    //Map application to infrastructure
    public UserEntity fromApplication(UserApplication userApplication) {
        if(userApplication == null){
            return null;
        }
        return new UserEntity(userApplication.getEmail(), userApplication.getPassword(), userApplication.getRole());
    }

    //Map application to infrastructure
    public UserEntity fromApplicationWithIdUser(UserApplication userApplication) {
        if(userApplication == null){
            return null;
        }
        return new UserEntity(userApplication.getId(), userApplication.getEmail(), userApplication.getPassword(), userApplication.getRole());
    }

    //Map infrastructure to application
    public UserApplication toApplication(UserEntity userEntity) {
        if(userEntity == null){
            return null;
        }
        return new UserApplication(userEntity.getId(), userEntity.getEmail(), userEntity.getPassword(), userEntity.getRole());
    }
}