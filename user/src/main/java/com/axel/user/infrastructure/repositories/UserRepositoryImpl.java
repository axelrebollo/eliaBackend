package com.axel.user.infrastructure.repositories;

import com.axel.user.application.repositories.IUserRepository;
import com.axel.user.domain.entities.User;
import com.axel.user.infrastructure.adapters.UserAdapterInfrastructure;
import com.axel.user.infrastructure.exceptions.InfrastructureException;
import com.axel.user.infrastructure.persistence.JpaUserRepository;
import com.axel.user.infrastructure.JpaEntities.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements IUserRepository {
    //Dependency injection
    private final JpaUserRepository jpaUserRepository;
    private final UserAdapterInfrastructure userAdapterInfrastructure;

    //Constructor
    public UserRepositoryImpl(JpaUserRepository jpaUserRepository,
                              UserAdapterInfrastructure userAdapterInfrastructure) {
        this.jpaUserRepository = jpaUserRepository;
        this.userAdapterInfrastructure = userAdapterInfrastructure;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userAdapterInfrastructure.fromApplication(user);
        userEntity = jpaUserRepository.save(userEntity);
        return userAdapterInfrastructure.toApplication(userEntity);
    }

    @Override
    public User findByEmail(String email) {
        UserEntity userEntity;
        try{
            userEntity = jpaUserRepository.findByEmail(email);
        }
        catch(InfrastructureException e){
            throw new InfrastructureException("Hubo un error con la búsqueda en la base de datos: ",e);
        }
        return userAdapterInfrastructure.toApplication(userEntity);
    }

    @Override
    public User findByIdUser(int idUser) {
        UserEntity userEntity = jpaUserRepository.findByIdUser(idUser);
        return userAdapterInfrastructure.toApplication(userEntity);
    }
}
