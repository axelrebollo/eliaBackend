package com.axel.user.infrastructure.repositories;

import com.axel.user.application.DTOs.UserApplication;
import com.axel.user.application.repositories.UserRepository;

import com.axel.user.infrastructure.adapters.UserAdapterInfrastructure;
import com.axel.user.infrastructure.persistence.JpaRepository;
import com.axel.user.infrastructure.JpaEntities.UserEntity;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JpaRepository jpaUserRepository;
    private final UserAdapterInfrastructure userAdapterInfrastructure = new UserAdapterInfrastructure();

    public UserRepositoryImpl(JpaRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public UserApplication save(UserApplication user) {
        UserEntity userEntity = userAdapterInfrastructure.fromApplication(user);
        userEntity = jpaUserRepository.save(userEntity);
        return userAdapterInfrastructure.toApplication(userEntity);
    }

    @Override
    public UserApplication findByEmail(String email) {
        UserEntity userEntity = jpaUserRepository.findByEmail(email);
        return userAdapterInfrastructure.toApplication(userEntity);
    }
}
