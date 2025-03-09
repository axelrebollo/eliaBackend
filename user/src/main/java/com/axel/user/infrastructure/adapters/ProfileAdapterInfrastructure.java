package com.axel.user.infrastructure.adapters;

import com.axel.user.application.DTOs.ProfileApplication;
import com.axel.user.domain.entities.User;
import com.axel.user.infrastructure.JpaEntities.ProfileEntity;
import com.axel.user.infrastructure.JpaEntities.UserEntity;
import com.axel.user.infrastructure.repositories.UserRepositoryImpl;
import org.springframework.stereotype.Service;

@Service
public class ProfileAdapterInfrastructure {

    //Dependency injection
    private final UserRepositoryImpl userRepositoryImpl;
    private final UserAdapterInfrastructure userAdapterInfrastructure;

    //Constructor
    public ProfileAdapterInfrastructure(UserRepositoryImpl userRepositoryImpl, UserAdapterInfrastructure userAdapterInfrastructure) {
        this.userRepositoryImpl = userRepositoryImpl;
        this.userAdapterInfrastructure = userAdapterInfrastructure;
    }

    //Map application to infrastructure
    public ProfileEntity fromApplication(ProfileApplication profileApplication){
        if(profileApplication == null){
            return null;
        }

        //Find User into database and parse to UserEntity
        User userApplication = userRepositoryImpl.findByIdUser(profileApplication.getIdUser());

        //From userApplication to UserEntity
        UserAdapterInfrastructure userAdapterInfrastructure = new UserAdapterInfrastructure();
        UserEntity userEntity = userAdapterInfrastructure.fromApplicationWithIdUser(userApplication);

        return new ProfileEntity(profileApplication.getIdProfile(),
                userEntity, profileApplication.getName(),
                profileApplication.getSurname1(), profileApplication.getSurname2());
    }

    //Map infrastructure to application
    public ProfileApplication toApplication(ProfileEntity profileEntity){
        if(profileEntity == null){
            return null;
        }
        return new ProfileApplication(profileEntity.getId(), profileEntity.getUser().getId(), profileEntity.getName(),
                profileEntity.getSurname1(), profileEntity.getSurname2());
    }
}
