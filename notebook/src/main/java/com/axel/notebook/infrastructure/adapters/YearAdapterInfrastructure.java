package com.axel.notebook.infrastructure.adapters;

import com.axel.notebook.application.DTOs.YearApplication;
import com.axel.notebook.infrastructure.JpaEntities.YearEntity;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import org.springframework.stereotype.Service;

@Service
public class YearAdapterInfrastructure {

    //Constructor
    public YearAdapterInfrastructure() {}

    public YearApplication toApplication(YearEntity yearEntity) {
        if(yearEntity == null){
            throw new InfrastructureException("La entidad de infrastructura está vacía.");
        }
        return new YearApplication(yearEntity.getIdYear(), yearEntity.getNameYear(), yearEntity.getIdProfile());
    }

    public YearEntity fromApplicationWhithoutId(YearApplication yearApplication) {
        if(yearApplication == null){
            throw new InfrastructureException("La entidad de aplicación está vacía.");
        }
        return new YearEntity(yearApplication.getNameYear(), yearApplication.getIdProfile());
    }
}
