package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.ITableRepository;
import com.axel.notebook.domain.entities.Table;
import com.axel.notebook.infrastructure.JpaEntities.TableEntity;
import com.axel.notebook.infrastructure.adapters.TableAdapterInfrastructure;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaTableRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TableRepositoryImpl implements ITableRepository {
    //Dependency injection
    private final JpaTableRepository jpaTableRepository;
    private final TableAdapterInfrastructure tableAdapter;

    //Constructor
    public TableRepositoryImpl(JpaTableRepository jpaTableRepository, TableAdapterInfrastructure tableAdapter) {
        this.jpaTableRepository = jpaTableRepository;
        this.tableAdapter = tableAdapter;
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

    public Table createTable(Table table){
        if(table == null){
            throw new InfrastructureException("La tabla para guardar es nula o vacía.");
        }
        TableEntity tableEntity = tableAdapter.fromApplicationWithoutId(table);

        if(jpaTableRepository.exists(table.getIdTeacher(), table.getNameTable(), table.getIdGroup())){
           throw new InfrastructureException("La tabla ya existe en el sistema.");
        }

        tableEntity = jpaTableRepository.save(tableEntity);
        return tableAdapter.toApplication(tableEntity);
    }
}
