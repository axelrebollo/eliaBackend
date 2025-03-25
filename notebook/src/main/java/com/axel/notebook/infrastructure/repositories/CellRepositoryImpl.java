package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.ICellRepository;
import com.axel.notebook.domain.valueObjects.Task;
import com.axel.notebook.infrastructure.JpaEntities.CellEntity;
import com.axel.notebook.infrastructure.JpaEntities.TaskCellEntity;
import com.axel.notebook.infrastructure.adapters.TaskCellAdapterInfrastructure;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaCellRepository;
import com.axel.notebook.infrastructure.persistence.JpaTaskCellRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CellRepositoryImpl implements ICellRepository {
    //Dependency injection
    private final JpaCellRepository jpaCellRepository;
    private final JpaTaskCellRepository jpaTaskCellRepository;
    private final TaskCellAdapterInfrastructure taskCellAdapterInfrastructure;

    //Constructor
    public CellRepositoryImpl(JpaCellRepository jpaCellRepository, JpaTaskCellRepository jpaTaskCellRepository, TaskCellAdapterInfrastructure taskCellAdapterInfrastructure) {
        this.jpaCellRepository = jpaCellRepository;
        this.jpaTaskCellRepository = jpaTaskCellRepository;
        this.taskCellAdapterInfrastructure = taskCellAdapterInfrastructure;
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

    //get position row and column from idCell
    public int[] getPositionsByIdCell(int idCell){
        if(idCell <= 0){
            throw new InfrastructureException("El identificador de la celda es erróneo o no existe.");
        }

        int[] listPositions = new int[2];

        Optional<CellEntity> cell = jpaCellRepository.findById(idCell);

        if(cell.isEmpty()){
            throw new InfrastructureException("La celda no existe en la base de datos.");
        }

        listPositions[0] = cell.get().getPositionRow();
        listPositions[1] = cell.get().getPositionCol();

        return listPositions;
    }

    public Task getTaskByIdCell(int idCell){
        if(idCell <= 0){
            throw new InfrastructureException("Error al recuperar la tarea en la base de datos.");
        }
        TaskCellEntity taskEntity = jpaTaskCellRepository.findById(idCell).get();
        if(taskEntity == null){
            return null;
        }
        return taskCellAdapterInfrastructure.toApplication(taskEntity);
    }
}
