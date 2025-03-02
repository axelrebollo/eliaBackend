package com.axel.user.infrastructure.persistence;

import com.axel.user.infrastructure.JpaEntities.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Integer> {
    //Save user into database
    public UserEntity save(UserEntity userEntity);

    //Find user by email into database
    public UserEntity findByEmail(String email);

    //Find user by id into database
    public UserEntity findByIdUser(int idUser);
}
