package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.ITaskCellRepository;
import com.axel.notebook.domain.entities.Table;
import com.axel.notebook.domain.valueObjects.Task;
import com.axel.notebook.infrastructure.JpaEntities.TableEntity;
import com.axel.notebook.infrastructure.JpaEntities.TaskCellEntity;
import com.axel.notebook.infrastructure.adapters.TaskCellAdapterInfrastructure;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaTableRepository;
import com.axel.notebook.infrastructure.persistence.JpaTaskCellRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TaskCellRepositoryImpl implements ITaskCellRepository {
    //Dependency injection
    private final JpaTaskCellRepository jpaTaskCellRepository;
    private final TaskCellAdapterInfrastructure taskCellAdapter;
    private final JpaTableRepository jpaTableRepository;

    //Constructor
    public TaskCellRepositoryImpl(JpaTaskCellRepository jpaTaskCellRepository,
                                  TaskCellAdapterInfrastructure taskCellAdapter, JpaTableRepository jpaTableRepository) {
        this.jpaTaskCellRepository = jpaTaskCellRepository;
        this.taskCellAdapter = taskCellAdapter;
        this.jpaTableRepository = jpaTableRepository;
    }

    public Task getTaskByIdCell(int idCell){
        if(idCell <= 0){
            throw new InfrastructureException("El identificador de la celda está vacío o es erróneo.");
        }

        TaskCellEntity taskCellEntity = jpaTaskCellRepository.findByIdCell(idCell);

        if(taskCellEntity == null){
            throw new InfrastructureException("La búsqueda de la tarea no ha encontrado tareas.");
        }
        return taskCellAdapter.toApplication(taskCellEntity);
    }

    public Task addTask(Task task, Table table){
        if(task == null){
            throw new InfrastructureException("La tarea creada no puede ser nula.");
        }

        TableEntity tableEntity = jpaTableRepository.findById(table.getIdTable());
        if(tableEntity == null){
            throw new InfrastructureException("La tabla no existe en el sistema.");
        }

        TaskCellEntity taskCellEntity = new TaskCellEntity(tableEntity, task.getPositionRow(), task.getPositionCol(), task.getNameTask());
        taskCellEntity = jpaTaskCellRepository.save(taskCellEntity);
        if(taskCellEntity == null){
            throw new InfrastructureException("Error al guardar la tarea en la base de datos.");
        }

        return taskCellAdapter.toApplication(taskCellEntity);
    }

    public TaskCellEntity getTaskCellEntityById(int idCell){
        if(idCell <= 0){
            throw new InfrastructureException("El identificador de la celda está vacío o es erróneo.");
        }

        TaskCellEntity taskCellEntity = jpaTaskCellRepository.findByIdCell(idCell);

        if(taskCellEntity == null){
            throw new InfrastructureException("La búsqueda de la tarea no ha encontrado tareas.");
        }
        return taskCellEntity;
    }
}
