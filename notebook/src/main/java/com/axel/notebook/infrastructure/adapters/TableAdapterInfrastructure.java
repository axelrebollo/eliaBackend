package com.axel.notebook.infrastructure.adapters;

import com.axel.notebook.domain.entities.Table;
import com.axel.notebook.infrastructure.JpaEntities.GroupEntity;
import com.axel.notebook.infrastructure.JpaEntities.TableEntity;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaGroupRepository;
import org.springframework.stereotype.Service;

@Service
public class TableAdapterInfrastructure {
    //Dependency injection
    private final JpaGroupRepository jpaGroupRepository;

    //constructor
    public TableAdapterInfrastructure(JpaGroupRepository jpaGroupRepository) {
        this.jpaGroupRepository = jpaGroupRepository;
    }

    public Table toApplication(TableEntity tableEntity) {
        if(tableEntity == null){
            throw new InfrastructureException("La entidad de infrastructura está vacía.");
        }
        return new Table(tableEntity.getIdTable(), tableEntity.getNameTable(), tableEntity.getIdProfile(),
                tableEntity.getClassCode(), tableEntity.getGroup().getIdGroup());
    }

    public TableEntity fromApplicationWithoutId(Table table) {
        if(table == null){
            throw new InfrastructureException("La entidad de aplicación está vacía.");
        }
        GroupEntity groupEntity = jpaGroupRepository.findById(table.getIdGroup());
        return new TableEntity(table.getNameTable(), table.getIdTeacher(), groupEntity);
    }
}
