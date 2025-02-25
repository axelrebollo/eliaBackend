package com.axel.user.infrastructure.persistence;

import com.axel.user.infrastructure.JpaEntities.UserEntity;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Integer> {
    //save user into database
    public UserEntity save(UserEntity userEntity);

    //find user by email into database
    public UserEntity findByEmail(String email);

    //find user by id into database
    public UserEntity findByIdUser(int idUser);
}
