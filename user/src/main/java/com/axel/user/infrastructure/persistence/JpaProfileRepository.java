package com.axel.user.infrastructure.persistence;

import com.axel.user.infrastructure.JpaEntities.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProfileRepository extends JpaRepository<ProfileEntity, Integer> {
    //Find by user
    public ProfileEntity findByUser_Id(int idUser);

    //Create profile
    public ProfileEntity save(ProfileEntity profile);

    //find by idProfile
    public ProfileEntity findByIdProfile(int idProfile);

    //find by name
    @Query("SELECT s FROM ProfileEntity s WHERE s.name = :nameProfile AND s.surname1 = :surname_1 AND s.surname2 = :surname_2 ")
    public ProfileEntity findByName(String nameProfile, String surname_1, String surname_2);
}
