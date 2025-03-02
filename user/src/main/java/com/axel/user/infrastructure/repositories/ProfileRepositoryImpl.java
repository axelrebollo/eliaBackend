package com.axel.user.infrastructure.repositories;

import com.axel.user.application.DTOs.ProfileApplication;
import com.axel.user.application.repositories.IProfileRepository;
import com.axel.user.infrastructure.JpaEntities.ProfileEntity;
import com.axel.user.infrastructure.adapters.ProfileAdapterInfrastructure;
import com.axel.user.infrastructure.adapters.UserAdapterInfrastructure;
import com.axel.user.infrastructure.exceptions.InfrastructureException;
import com.axel.user.infrastructure.persistence.JpaProfileRepository;
import com.axel.user.infrastructure.persistence.JpaUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileRepositoryImpl implements IProfileRepository {
    private final JpaProfileRepository jpaProfileRepository;
    private final ProfileAdapterInfrastructure profileAdapterInfrastructure;
    private final UserAdapterInfrastructure userAdapterInfrastructure;

    public ProfileRepositoryImpl(JpaUserRepository jpaUserRepository,
                                 ProfileAdapterInfrastructure profileAdapterInfrastructure,
                                 JpaProfileRepository jpaProfileRepository, UserAdapterInfrastructure userAdapterInfrastructure) {
        this.profileAdapterInfrastructure = profileAdapterInfrastructure;
        this.jpaProfileRepository = jpaProfileRepository;
        this.userAdapterInfrastructure = userAdapterInfrastructure;
    }

    public ProfileApplication findProfileByIdUser(int idUser){
        ProfileEntity profileEntity;
        try{
            profileEntity = jpaProfileRepository.findByUserId(idUser);
        }
        catch(InfrastructureException e){
            throw new InfrastructureException("Error en la base de datos al buscar el perfil de usuario.");
        }
        return profileAdapterInfrastructure.toApplication(profileEntity);
    }

    public ProfileApplication addProfile(ProfileApplication profile){
        ProfileEntity profileEntity = profileAdapterInfrastructure.fromApplication(profile);
        try{
            profileEntity = jpaProfileRepository.save(profileEntity);
        }
        catch(InfrastructureException e){
            throw new InfrastructureException("Error en la base de datos al crear el perfil de usuario.");
        }
        return profileAdapterInfrastructure.toApplication(profileEntity);
    }
}
