package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.ITaskCellRepository;
import com.axel.notebook.domain.valueObjects.Task;
import com.axel.notebook.infrastructure.JpaEntities.TaskCellEntity;
import com.axel.notebook.infrastructure.adapters.TaskCellAdapterInfrastructure;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaTaskCellRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TaskCellRepositoryImpl implements ITaskCellRepository {
    private final JpaTaskCellRepository jpaTaskCellRepository;
    private final TaskCellAdapterInfrastructure taskCellAdapter;
    //Dependency injection

    //Constructor
    public TaskCellRepositoryImpl(JpaTaskCellRepository jpaTaskCellRepository,
                                  TaskCellAdapterInfrastructure taskCellAdapter) {
        this.jpaTaskCellRepository = jpaTaskCellRepository;
        this.taskCellAdapter = taskCellAdapter;
    }

    public Task getNameByIdCell(int idCell){
        if(idCell <= 0){
            throw new InfrastructureException("El identificador de la celda está vacío o es erróneo.");
        }

        TaskCellEntity taskCellEntity = jpaTaskCellRepository.findByIdCell(idCell);

        if(taskCellEntity == null){
            throw new InfrastructureException("La búsqueda de la tarea no ha encontrado tareas.");
        }
        return taskCellAdapter.toApplication(taskCellEntity);
    }
}
