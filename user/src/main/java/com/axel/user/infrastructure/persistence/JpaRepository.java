package com.axel.user.infrastructure.persistence;

import com.axel.user.infrastructure.JpaEntities.UserEntity;

import org.springframework.stereotype.Repository;

@Repository
public interface JpaRepository extends org.springframework.data.jpa.repository.JpaRepository<UserEntity, Long> {
    //save user into database
    public UserEntity save(UserEntity userEntity);

    //find user by email into database
    public UserEntity findByEmail(String email);
}
