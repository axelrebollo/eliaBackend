package com.axel.notebook.infrastructure.adapters;

import com.axel.notebook.domain.entities.Year;
import com.axel.notebook.infrastructure.JpaEntities.YearEntity;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import org.springframework.stereotype.Service;

@Service
public class YearAdapterInfrastructure {

    //Constructor
    public YearAdapterInfrastructure() {}

    public Year toApplication(YearEntity yearEntity) {
        if(yearEntity == null){
            throw new InfrastructureException("La entidad de infrastructura está vacía.");
        }
        return new Year(yearEntity.getIdYear(), yearEntity.getNameYear(), yearEntity.getIdProfile());
    }

    public YearEntity fromApplicationWhithoutId(Year year) {
        if(year == null){
            throw new InfrastructureException("La entidad de aplicación está vacía.");
        }
        return new YearEntity(year.getNameYear(), year.getIdProfile());
    }
}
