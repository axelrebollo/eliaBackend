package com.axel.user.infrastructure.repositories;

import com.axel.user.application.DTOs.ProfileApplication;
import com.axel.user.application.repositories.IProfileRepository;
import com.axel.user.infrastructure.JpaEntities.ProfileEntity;
import com.axel.user.infrastructure.adapters.ProfileAdapterInfrastructure;
import com.axel.user.infrastructure.persistence.JpaProfileRepository;
import com.axel.user.infrastructure.persistence.JpaUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileRepositoryImpl implements IProfileRepository {
    private final JpaProfileRepository jpaProfileRepository;
    private final ProfileAdapterInfrastructure profileAdapterInfrastructure;

    public ProfileRepositoryImpl(JpaUserRepository jpaUserRepository,
                                 ProfileAdapterInfrastructure profileAdapterInfrastructure,
                                 JpaProfileRepository jpaProfileRepository) {
        this.profileAdapterInfrastructure = profileAdapterInfrastructure;
        this.jpaProfileRepository = jpaProfileRepository;
    }

    public ProfileApplication findProfileByIdUser(int idUser){
        ProfileEntity profileEntity = jpaProfileRepository.findByUserId(idUser);
        return profileAdapterInfrastructure.toApplication(profileEntity);
    }
}
