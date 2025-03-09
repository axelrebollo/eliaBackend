package com.axel.user.infrastructure.repositories;

import com.axel.user.domain.entities.Profile;
import com.axel.user.application.repositories.IProfileRepository;
import com.axel.user.infrastructure.JpaEntities.ProfileEntity;
import com.axel.user.infrastructure.adapters.ProfileAdapterInfrastructure;
import com.axel.user.infrastructure.exceptions.InfrastructureException;
import com.axel.user.infrastructure.persistence.JpaProfileRepository;
import com.axel.user.infrastructure.persistence.JpaUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileRepositoryImpl implements IProfileRepository {
    //Dependency injection
    private final JpaProfileRepository jpaProfileRepository;
    private final ProfileAdapterInfrastructure profileAdapterInfrastructure;

    //Constructor
    public ProfileRepositoryImpl(JpaUserRepository jpaUserRepository,
                                 ProfileAdapterInfrastructure profileAdapterInfrastructure,
                                 JpaProfileRepository jpaProfileRepository) {
        this.profileAdapterInfrastructure = profileAdapterInfrastructure;
        this.jpaProfileRepository = jpaProfileRepository;
    }

    //Find profile by idUser
    public Profile findProfileByIdUser(int idUser){
        ProfileEntity profileEntity;
        try{
            profileEntity = jpaProfileRepository.findByUser_Id(idUser);
        }
        catch(InfrastructureException e){
            throw new InfrastructureException("Error en la base de datos al buscar el perfil de usuario.");
        }
        return profileAdapterInfrastructure.toApplication(profileEntity);
    }

    //Create/update profile from ProfileApplication
    public Profile save(Profile profile){
        ProfileEntity profileEntity = profileAdapterInfrastructure.fromApplication(profile);
        try{
            profileEntity = jpaProfileRepository.save(profileEntity);
        }
        catch(InfrastructureException e){
            throw new InfrastructureException("Error en la base de datos al crear o actualizar el perfil de usuario.");
        }
        return profileAdapterInfrastructure.toApplication(profileEntity);
    }
}
