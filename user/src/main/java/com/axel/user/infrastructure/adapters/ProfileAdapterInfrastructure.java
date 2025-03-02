package com.axel.user.infrastructure.adapters;

import com.axel.user.application.DTOs.ProfileApplication;
import com.axel.user.application.DTOs.UserApplication;
import com.axel.user.infrastructure.JpaEntities.ProfileEntity;
import com.axel.user.infrastructure.JpaEntities.UserEntity;
import com.axel.user.infrastructure.repositories.UserRepositoryImpl;
import org.springframework.stereotype.Service;

@Service
public class ProfileAdapterInfrastructure {

    private final UserRepositoryImpl userRepositoryImpl;

    public ProfileAdapterInfrastructure(UserRepositoryImpl userRepositoryImpl) {
        this.userRepositoryImpl = userRepositoryImpl;
    }

    //Map application to infrastructure
    public ProfileEntity fromApplication(ProfileApplication profileApplication){
        if(profileApplication == null){
            return null;
        }

        //find User into database and parse to UserEntity
        UserApplication userApplication = userRepositoryImpl.findByIdUser(profileApplication.getIdUser());

        //from userApplication to UserEntity
        UserAdapterInfrastructure userAdapterInfrastructure = new UserAdapterInfrastructure();
        UserEntity userEntity = userAdapterInfrastructure.fromApplicationWithIdUser(userApplication);

        return new ProfileEntity(userEntity, profileApplication.getName(),
                profileApplication.getSurname1(), profileApplication.getSurname2());
    }

    public ProfileApplication toApplication(ProfileEntity profileEntity){
        if(profileEntity == null){
            return null;
        }
        return new ProfileApplication(profileEntity.getId(), profileEntity.getName(),
                profileEntity.getSurname1(), profileEntity.getSurname2());
    }
}
