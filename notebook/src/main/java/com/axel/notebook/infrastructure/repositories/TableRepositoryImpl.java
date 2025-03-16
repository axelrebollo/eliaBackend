package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.ITableRepository;
import com.axel.notebook.infrastructure.JpaEntities.TableEntity;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaTableRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TableRepositoryImpl implements ITableRepository {
    //Dependency injection
    private final JpaTableRepository jpaTableRepository;

    //Constructor
    public TableRepositoryImpl(JpaTableRepository jpaTableRepository) {
        this.jpaTableRepository = jpaTableRepository;
    }

    public List<String> getAllTablesForNameSubject(int idProfile, int idGroup){
        List<String> tables = new ArrayList<>();

        if(idProfile <= 0 || idGroup <= 0){
            throw new InfrastructureException("Error con el prefil de usuario o el grupo");
        }

        List<TableEntity> tablesEntities = jpaTableRepository.findAllTablesByIdGroup(idGroup);

        if(tablesEntities != null){
            for(TableEntity tableEntity : tablesEntities){
                tables.add(tableEntity.getNameTable());
            }
        }
        return tables;
    }
}
