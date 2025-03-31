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
import java.util.Objects;

@Repository
public class TableRepositoryImpl implements ITableRepository {
    //Dependency injection
    private final JpaTableRepository jpaTableRepository;
    private final TableAdapterInfrastructure tableAdapter;
    private final TableAdapterInfrastructure tableAdapterInfrastructure;

    //Constructor
    public TableRepositoryImpl(JpaTableRepository jpaTableRepository, TableAdapterInfrastructure tableAdapter, TableAdapterInfrastructure tableAdapterInfrastructure) {
        this.jpaTableRepository = jpaTableRepository;
        this.tableAdapter = tableAdapter;
        this.tableAdapterInfrastructure = tableAdapterInfrastructure;
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

    public Table findTable(int idProfile, int idGroup, String nameTable){
        if(idProfile <= 0 || idGroup <= 0 || nameTable == null){
            throw new InfrastructureException("Error al intentar buscar la tabla, algún parametro no es correcto.");
        }

        TableEntity table = jpaTableRepository.findByProfileGroupName(idProfile, idGroup, nameTable);
        return tableAdapterInfrastructure.toApplication(table);
    }

    public boolean existTableWithClassCode(String classCode){
        if(classCode == null){
            throw new InfrastructureException("El codigo de la clase es nulo.");
        }

        TableEntity table = jpaTableRepository.findByClassCode(classCode);

        if(table == null || !Objects.equals(table.getClassCode(), classCode)){
            return false;
        }
        return true;
    }

    public Table findTableByClassCode(String classCode){
        if(classCode == null){
            throw new InfrastructureException("El codigo de la clase es nulo.");
        }

        TableEntity table = jpaTableRepository.findByClassCode(classCode);

        if(table == null || !Objects.equals(table.getClassCode(), classCode)){
            throw new InfrastructureException("Error al recuperar la tabla con el codigo de la clase.");
        }

        return tableAdapterInfrastructure.toApplication(table);
    }

    public boolean deleteTable(String classCode){
        if(classCode == null || !existTableWithClassCode(classCode)){
            throw new InfrastructureException("El codigo de la clase es nulo o la clase no existe");
        }

        boolean isDeleted = false;
        int idTable = jpaTableRepository.findByClassCode(classCode).getIdTable();
        if(idTable <= 0){
            throw new InfrastructureException("La tabla no existe");
        }

        jpaTableRepository.deleteById(idTable);
        if(!existTableWithClassCode(classCode)){
            isDeleted = true;
        }

        return isDeleted;
    }
}
