package com.axel.user.infrastructure.persistence;

import com.axel.user.infrastructure.JpaEntities.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProfileRepository extends JpaRepository<ProfileEntity, Integer> {
    //Find by user
    public ProfileEntity findByUserId(int idUser);

    //Save (create) profile
    public ProfileEntity save(ProfileEntity profile);
}
