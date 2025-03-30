package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.ITaskCellRepository;
import com.axel.notebook.domain.entities.Table;
import com.axel.notebook.domain.valueObjects.Task;
import com.axel.notebook.infrastructure.JpaEntities.TableEntity;
import com.axel.notebook.infrastructure.JpaEntities.TaskCellEntity;
import com.axel.notebook.infrastructure.adapters.TaskCellAdapterInfrastructure;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaCellRepository;
import com.axel.notebook.infrastructure.persistence.JpaTableRepository;
import com.axel.notebook.infrastructure.persistence.JpaTaskCellRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;

@Repository
public class TaskCellRepositoryImpl implements ITaskCellRepository {
    //Dependency injection
    private final JpaTaskCellRepository jpaTaskCellRepository;
    private final TaskCellAdapterInfrastructure taskCellAdapter;
    private final JpaTableRepository jpaTableRepository;
    private final JpaCellRepository jpaCellRepository;
    private final NoteCellRepositoryImpl noteCellRepositoryImpl;

    //Constructor
    public TaskCellRepositoryImpl(JpaTaskCellRepository jpaTaskCellRepository,
                                  TaskCellAdapterInfrastructure taskCellAdapter,
                                  JpaTableRepository jpaTableRepository,
                                  JpaCellRepository jpaCellRepository,
                                  @Lazy NoteCellRepositoryImpl noteCellRepositoryImpl) {
        this.jpaTaskCellRepository = jpaTaskCellRepository;
        this.taskCellAdapter = taskCellAdapter;
        this.jpaTableRepository = jpaTableRepository;
        this.jpaCellRepository = jpaCellRepository;
        this.noteCellRepositoryImpl = noteCellRepositoryImpl;
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

    //delete task into table
    public boolean deleteTaskColumn(int idTable, int positionColumnTask){
        boolean isDeleted = false;
        if(idTable <= 0 || positionColumnTask <= 0){
            throw new InfrastructureException("El identificador de la tabla o columna no son correctos.");
        }

        //get all notes for this table and position column and delete notes
        List<Integer> idNotes = jpaCellRepository.findForTypeIdAndColumn(idTable, positionColumnTask, "NOTE");
        if(!idNotes.isEmpty()) {
            //delete all notes into noteCellEntity for this column and task
            for (Integer idNote : idNotes) {
                isDeleted = noteCellRepositoryImpl.deleteNote(idNote);
                if (!isDeleted) {
                    throw new InfrastructureException("Error al eliminar la nota en el sistema.");
                }
            }
        }

        //get task cell for table and positionColumnTask
        List<Integer> idCellTask = jpaCellRepository.findForTypeIdAndColumn(idTable, positionColumnTask, "TASK");
        if(idCellTask.size() != 1) {
            throw new InfrastructureException("No existe la tarea a borrar.");
        }
        //delete task into taskCellEntity for this idCell
        for (Integer idCell : idCellTask) {
            isDeleted = deleteTask(idCell);
            if (!isDeleted) {
                throw new InfrastructureException("Error al eliminar la tarea en el sistema.");
            }
        }

        //reallocate columns
        boolean isAllocated = allocateColumns(idTable, positionColumnTask);
        if(!isAllocated){
            throw new InfrastructureException("Error, no se han recolocado correctamente las celdas de la tabla.");
        }
        return isDeleted;
    }

    //delete a specific task into taskCellEntity
    public boolean deleteTask(int idCell){
        if(idCell <= 0){
            throw new InfrastructureException("El identificador de la tararea no es correcto.");
        }
        //delete task into taskCellEntity
        jpaTaskCellRepository.deleteByIdCell(idCell);
        //check if is deleted correctly if not deleted correctly return false else true
        return jpaTaskCellRepository.findByIdCell(idCell) == null;
    }

    public boolean allocateColumns(int idTable, int positionColumnTask){
        List<Object[]> columnsTask = jpaCellRepository.getAllByIdAndType(idTable, "TASK");
        List<Object[]> columnsNote = jpaCellRepository.getAllByIdAndType(idTable, "NOTE");
        //exist columns and is necessary allocate
        if(!columnsTask.isEmpty()){
            //sort tasks for column, minus col to max col
            columnsTask.sort(Comparator.comparingInt(a -> (int) a[1]));

            for(Object[] column : columnsTask){
                int idCell = (int)column[0];
                int positionColumn = (int)column[1];
                if(positionColumn > positionColumnTask){
                    //update to new position
                    jpaCellRepository.setPositionCol(idCell, positionColumn-1);
                }
            }

            //sort notes for column, minus col to max col
            columnsNote.sort(Comparator.comparingInt(a -> (int) a[1]));

            //exist notes
            if(!columnsNote.isEmpty()){
                for(Object[] column : columnsNote){
                    int idCell = (int)column[0];
                    int positionColumn = (int)column[1];
                    if(positionColumn > positionColumnTask){
                        //update to new position
                        jpaCellRepository.setPositionCol(idCell, positionColumn-1);
                    }
                }
            }
        }
        return true;
    }
}
