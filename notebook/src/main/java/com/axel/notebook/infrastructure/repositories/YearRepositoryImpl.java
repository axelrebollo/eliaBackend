package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.domain.entities.Year;
import com.axel.notebook.application.repositories.IYearRepository;
import com.axel.notebook.infrastructure.JpaEntities.YearEntity;
import com.axel.notebook.infrastructure.adapters.YearAdapterInfrastructure;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaYearRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class YearRepositoryImpl implements IYearRepository {

    //Dependency injection
    private final JpaYearRepository jpaYearRepository;
    private final YearAdapterInfrastructure yearAdapter;

    //Constructor
    public YearRepositoryImpl(JpaYearRepository jpaYearRepository, YearAdapterInfrastructure yearAdapter) {
        this.jpaYearRepository = jpaYearRepository;
        this.yearAdapter = yearAdapter;
    }

    //find all years for user and check that this year not exists
    public Boolean existYearForUser(String name, int idProfile) {
        List<YearEntity> yearEntities = jpaYearRepository.findByIdProfile(idProfile);
        Boolean exist = false;
        for (YearEntity yearEntity : yearEntities) {
            if(yearEntity.getNameYear().equals(name)) {
                exist = true;
            }
        }
        return exist;
    }

    //update year that user are created
    public Year updateYear(Year year) {
        if(year == null) {
            throw new InfrastructureException("El año está vacío o es inexistente.");
        }
        YearEntity yearEntity = yearAdapter.fromApplicationWhithoutId(year);
        yearEntity = jpaYearRepository.save(yearEntity);
        return yearAdapter.toApplication(yearEntity);
    }

    //get all years for user
    public List<String> getAllYearsForUser(int idProfile) {
        List<YearEntity> yearEntities = jpaYearRepository.findByIdProfile(idProfile);
        if(yearEntities == null) {
            throw new InfrastructureException("No se han encontrado años.");
        }
        List<String> years = new ArrayList<>();

        for (YearEntity yearEntity : yearEntities) {
            years.add(yearEntity.getNameYear());
        }

        return years;
    }

    //delete year that user are created
    public void deleteYear(int idUser, int idYear) {
        //TODO
    }
}
