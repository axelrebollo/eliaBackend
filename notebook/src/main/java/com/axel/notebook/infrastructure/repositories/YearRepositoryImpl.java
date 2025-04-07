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
    public boolean existYearForUser(String name, int idProfile) {
        List<YearEntity> yearEntities = jpaYearRepository.findByIdProfile(idProfile);
        for (YearEntity yearEntity : yearEntities) {
            if(yearEntity.getNameYear().equals(name)) {
                return true;
            }
        }
        return false;
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
    public List<String> getAllYearsNameForUser(int idProfile) {
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

    //get all Years for user
    public List<Year> getAllYearsForUser(int idProfile) {
        List<YearEntity> yearEntities = jpaYearRepository.findByIdProfile(idProfile);
        if(yearEntities == null) {
            throw new InfrastructureException("No se han encontrado años.");
        }
        List<Year> years = new ArrayList<>();

        for (YearEntity yearEntity : yearEntities) {
            Year year = yearAdapter.toApplication(yearEntity);
            years.add(year);
        }
        return years;
    }

    public int getIdYearForUser(int idProfile,String nameYear) {
        if(existYearForUser(nameYear,idProfile)) {
            List<Year> years = getAllYearsForUser(idProfile);
            if(years.isEmpty()) {
                return 0;
            }
            else {
                for (Year year : years) {
                    if (year.getNameYear().equals(nameYear)) {
                        return year.getIdYear();
                    }
                }
            }
        }
        return 0;
    }

    //delete year that user are created
    public boolean deleteYear(int idProfile, String nameYear) {
        if(idProfile <= 0 || nameYear == null || nameYear.isEmpty()) {
            throw new InfrastructureException("Alguno de los datos para borrar el año no es correcto.");
        }

        boolean isDeleted = false;
        YearEntity year = jpaYearRepository.findByNameAndIdProfile(nameYear, idProfile);
        if(year == null){
            throw new InfrastructureException("No se ha encontrado el año.");
        }

        try{
            jpaYearRepository.deleteById(year.getIdYear());
            isDeleted = true;
        }
        catch(InfrastructureException e){
            throw new InfrastructureException("Error al eliminar el año.");
        }

        return isDeleted;
    }

    public int updateNameYear(int idProfile, String nameYear, String newNameYear){
        if(idProfile <= 0 || nameYear == null || nameYear.isEmpty() || newNameYear == null || newNameYear.isEmpty()) {
            throw new InfrastructureException("No es posible actualizar el nombre del año con los datos obtenidos.");
        }

        //TODO
        //recuperar todos los años y comprobar que el nuevo nombre no es igual a alguno existente

        YearEntity yearEntity = jpaYearRepository.findByNameAndIdProfile(nameYear, idProfile);
        if(yearEntity == null){
            throw new InfrastructureException("No se ha encontrado el año.");
        }

        int isUpdated = jpaYearRepository.updateNameByIdYear(yearEntity.getIdYear(), newNameYear);

        if(isUpdated == 1){
            return yearEntity.getIdYear();
        }
        return -1;
    }
}
