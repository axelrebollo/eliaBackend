package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.ICellRepository;
import com.axel.notebook.infrastructure.JpaEntities.CellEntity;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaCellRepository;
import com.axel.notebook.infrastructure.persistence.JpaNoteCellRepository;
import com.axel.notebook.infrastructure.persistence.JpaStudentCellRepository;
import com.axel.notebook.infrastructure.persistence.JpaTaskCellRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CellRepositoryImpl implements ICellRepository {
    //Dependency injection
    private final JpaCellRepository jpaCellRepository;
    private final JpaStudentCellRepository jpaStudentCellRepository;
    private final JpaTaskCellRepository jpaTaskCellRepository;
    private final JpaNoteCellRepository jpaNoteCellRepository;

    //Constructor
    public CellRepositoryImpl(JpaCellRepository jpaCellRepository, JpaStudentCellRepository jpaStudentCellRepository, JpaTaskCellRepository jpaTaskCellRepository, JpaNoteCellRepository jpaNoteCellRepository) {
        this.jpaCellRepository = jpaCellRepository;
        this.jpaStudentCellRepository = jpaStudentCellRepository;
        this.jpaTaskCellRepository = jpaTaskCellRepository;
        this.jpaNoteCellRepository = jpaNoteCellRepository;
    }

    public List<Object[]> getCellsForIdTableAndType(int idTable, String type){
        if(idTable <= 0){
            throw new InfrastructureException("El id de la tabla está vacío y no es correcto.");
        }

        if(type == null || type.isEmpty()){
            throw new InfrastructureException("El tipo de celda debe especificarse.");
        }

        //columns from cellEntity
        List<Object[]> listColumns = new ArrayList<>();

        if(type.equals("STUDENT")){
            listColumns = jpaCellRepository.getAllByIdAndType(idTable, type);
        }
        else if(type.equals("TASK")){
            listColumns = jpaCellRepository.getAllByIdAndType(idTable, type);
        }
        else if(type.equals("NOTE")){
            listColumns = jpaCellRepository.getAllByIdAndType(idTable, type);
        }
        else{
            return listColumns;
        }

        return listColumns;
    }
}
