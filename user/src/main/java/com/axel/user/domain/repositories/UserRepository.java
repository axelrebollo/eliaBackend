package com.axel.user.domain.repositories;

import com.axel.user.infrastructure.JpaEntities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    //save user into database
    public UserEntity save(UserEntity userEntity);

    //find user by email into database
    public UserEntity findByEmail(String email);
}
