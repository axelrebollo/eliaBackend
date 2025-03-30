package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.ICellRepository;
import com.axel.notebook.domain.valueObjects.Task;
import com.axel.notebook.infrastructure.JpaEntities.*;
import com.axel.notebook.infrastructure.adapters.TaskCellAdapterInfrastructure;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.kafka.producers.CellProducer;
import com.axel.notebook.infrastructure.persistence.*;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CellRepositoryImpl implements ICellRepository {
    //Dependency injection
    private final JpaCellRepository jpaCellRepository;
    private final JpaTaskCellRepository jpaTaskCellRepository;
    private final TaskCellAdapterInfrastructure taskCellAdapterInfrastructure;
    private final JpaTableRepository jpaTableRepository;
    private final JpaStudentCellRepository jpaStudentCellRepository;
    private final CellProducer cellProducer;
    private final JpaNoteCellRepository jpaNoteCellRepository;

    //Constructor
    public CellRepositoryImpl(JpaCellRepository jpaCellRepository, JpaTaskCellRepository jpaTaskCellRepository, TaskCellAdapterInfrastructure taskCellAdapterInfrastructure, JpaTableRepository jpaTableRepository, JpaStudentCellRepository jpaStudentCellRepository, CellProducer cellProducer, JpaNoteCellRepository jpaNoteCellRepository) {
        this.jpaCellRepository = jpaCellRepository;
        this.jpaTaskCellRepository = jpaTaskCellRepository;
        this.taskCellAdapterInfrastructure = taskCellAdapterInfrastructure;
        this.jpaTableRepository = jpaTableRepository;
        this.jpaStudentCellRepository = jpaStudentCellRepository;
        this.cellProducer = cellProducer;
        this.jpaNoteCellRepository = jpaNoteCellRepository;
    }

    //get all cells for idTable and type cell
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

    //get task domain by idCell
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

    //move position column cell
    public void movePositionColCell(int idTaskCell, int newTaskPositionCol) {
        if (idTaskCell <= 0 || newTaskPositionCol <= 0) {
            throw new InfrastructureException("La nueva posición de la columna o el identificador de celda no son correctos.");
        }

        try {
            jpaCellRepository.setPositionCol(idTaskCell, newTaskPositionCol);
        } catch (InfrastructureException e) {
            throw new InfrastructureException(e.getMessage());
        }
    }

    //get id cell
    public int getIdCell(String name, String classCode, String type){
        if(name == null || name.isEmpty() || classCode == null || classCode.isEmpty() || type == null || type.isEmpty()){
            throw new InfrastructureException("Error, algún parámetro es nulo o vacío.");
        }

        //get idTable from JPAtableEntity(classCode)
        int idTable = jpaTableRepository.findByClassCode(classCode).getIdTable();
        if(idTable <= 0){
            throw new InfrastructureException("Error, la tabla no existe en el sistema.");
        }

        //get idCell task/student from name and idTable
        if(type.equals("STUDENT")){
            Map<String, String> studentProfile = cellProducer.sendNameProfile(name);
            int idProfile = Integer.parseInt(studentProfile.get("idProfile"));
            List<StudentCellEntity> studentsInAllTables = jpaStudentCellRepository.findStudentCellEntityByIdProfile(idProfile);
            if(studentsInAllTables.isEmpty()){
                return 0;
            }

            for(StudentCellEntity studentCellEntity : studentsInAllTables){
                if(classCode.equals(studentCellEntity.getTable().getClassCode())){
                    return studentCellEntity.getIdCell();
                }
            }
        }
        else if(type.equals("TASK")){
            List<TaskCellEntity> tasksForName = jpaTaskCellRepository.findByName(name);
            if(tasksForName.isEmpty()){
                return 0;
            }

            for(TaskCellEntity taskCellEntity : tasksForName){
                if(classCode.equals(taskCellEntity.getTable().getClassCode())){
                    return taskCellEntity.getIdCell();
                }
            }
        }
        else{
            throw new InfrastructureException("El tipo de celda debe especificarse.");
        }
        return 0;
    }

    //get id note cell
    public int getIdNoteCell(int idCellStudent, int idCellTask){
        if(idCellStudent <= 0 || idCellTask <= 0){
            throw new InfrastructureException("Alguno de los valores para recuperar la nota está vacío.");
        }

        int idNoteCell = jpaNoteCellRepository.findIdNoteCell(idCellStudent, idCellTask).getIdCell();
        if(idNoteCell <= 0){
            return 0;
        }
        return idNoteCell;
    }

    //update note cell
    public int updateNote(int idCellNote, double newNote){
        if(idCellNote <= 0 || newNote < 0){
            throw new InfrastructureException("Error, el identificador de la nota o la nota no son correctos.");
        }

        int idNote = jpaNoteCellRepository.updateNote(idCellNote, newNote);
        if(idNote <= 0){
            throw new InfrastructureException("Error, hay algún problema con la actualización de la nota.");
        }
        return idNote;
    }

    //return boolean if exist task into table
    public boolean taskExistIntoTable(String classCode, String nameNewTask){
        if(classCode == null || classCode.isEmpty() || nameNewTask == null || nameNewTask.isEmpty()){
            throw new InfrastructureException("Error, el codigo o el nombre de la tabla no son correctos.");
        }

        //get table
        TableEntity table = jpaTableRepository.findByClassCode(classCode);
        if(table == null){
            throw new InfrastructureException("Error, la tabla no existe en el sistema.");
        }

        //get all cells tasks into table
        List<Object[]> tasks = jpaCellRepository.getAllByIdAndType(table.getIdTable(), "TASK");
        if(tasks.isEmpty()){
            return false;
        }

        for(Object[] task : tasks){
            int idTask = (Integer) task[0];
            TaskCellEntity taskEntity = jpaTaskCellRepository.findByIdCell(idTask);
            if(taskEntity != null && taskEntity.getNameTask().equals(nameNewTask)){
                return true;
            }
        }
        return false;
    }
}
