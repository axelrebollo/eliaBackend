package com.axel.notebook.infrastructure.adapters;

import com.axel.notebook.domain.valueObjects.Task;
import com.axel.notebook.infrastructure.JpaEntities.TaskCellEntity;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaTaskCellRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskCellAdapterInfrastructure {
    //Dependency injection
    private final JpaTaskCellRepository jpaTaskCellRepository;

    //Constructor
    public TaskCellAdapterInfrastructure(JpaTaskCellRepository jpaTaskCellRepository) {
        this.jpaTaskCellRepository = jpaTaskCellRepository;
    }

    public Task toApplication(TaskCellEntity taskCellEntity){
        if(taskCellEntity == null){
            throw new InfrastructureException("La entidad de infrastructura está vacía.");
        }
        return new Task(taskCellEntity.getIdCell(), taskCellEntity.getNameTask(),
                taskCellEntity.getPositionRow(), taskCellEntity.getPositionCol());
    }

    public TaskCellEntity fromApplication(Task task){
        if(task == null){
            throw new InfrastructureException("La entidad de aplicación está vacía.");
        }
        TaskCellEntity taskCellEntity = jpaTaskCellRepository.findByIdCell(task.getIdTask());
        return new TaskCellEntity(taskCellEntity.getTable(), taskCellEntity.getPositionRow(),
                taskCellEntity.getPositionCol(), taskCellEntity.getNameTask());
    }
}
