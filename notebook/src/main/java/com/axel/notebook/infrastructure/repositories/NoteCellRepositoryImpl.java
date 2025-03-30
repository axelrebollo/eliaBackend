package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.INoteCellRepository;
import com.axel.notebook.domain.entities.Table;
import com.axel.notebook.domain.valueObjects.Note;
import com.axel.notebook.domain.valueObjects.Task;
import com.axel.notebook.infrastructure.JpaEntities.NoteCellEntity;
import com.axel.notebook.infrastructure.JpaEntities.StudentCellEntity;
import com.axel.notebook.infrastructure.JpaEntities.TableEntity;
import com.axel.notebook.infrastructure.JpaEntities.TaskCellEntity;
import com.axel.notebook.infrastructure.adapters.NoteAdapterInfrastructure;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaNoteCellRepository;
import com.axel.notebook.infrastructure.persistence.JpaTableRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository
public class NoteCellRepositoryImpl implements INoteCellRepository {
    //Dependency injection
    private final JpaNoteCellRepository jpaNoteCellRepository;
    private final NoteAdapterInfrastructure noteAdapterInfrastructure;
    private final JpaTableRepository jpaTableRepository;
    private final StudentCellRepositoryImpl studentCellRepositoryImpl;
    private final TaskCellRepositoryImpl taskCellRepositoryImpl;

    //Constructor
    public NoteCellRepositoryImpl(JpaNoteCellRepository jpaNoteCellRepository,
                                  NoteAdapterInfrastructure noteAdapterInfrastructure,
                                  JpaTableRepository jpaTableRepository,
                                  StudentCellRepositoryImpl studentCellRepositoryImpl,
                                  @Lazy TaskCellRepositoryImpl taskCellRepositoryImpl) {
        this.jpaNoteCellRepository = jpaNoteCellRepository;
        this.noteAdapterInfrastructure = noteAdapterInfrastructure;
        this.jpaTableRepository = jpaTableRepository;
        this.studentCellRepositoryImpl = studentCellRepositoryImpl;
        this.taskCellRepositoryImpl = taskCellRepositoryImpl;
    }

    public Note getNoteForId(int idNote){
        if(idNote <= 0){
            throw new InfrastructureException("El identificador de la celda es erróneo o no existe.");
        }

        NoteCellEntity noteEntity = jpaNoteCellRepository.findByIdCell(idNote);

        if(noteEntity == null){
            throw new InfrastructureException("No se ha encontrado la nota el la base de datos");
        }

        return noteAdapterInfrastructure.toApplication(noteEntity);
    }

    public Note addNote(Note note, Table table, Task task){
        if(note == null){
            throw new InfrastructureException("Error, la nota es nula.");
        }

        TableEntity tableEntity = jpaTableRepository.findById(table.getIdTable());
        if(tableEntity == null){
            throw new InfrastructureException("La tabla no existe en el sistema.");
        }

        NoteCellEntity noteEntity = new NoteCellEntity(tableEntity, note.getPositionRow(), note.getPositionCol(), note.getNote());

        int idCellStudent = studentCellRepositoryImpl.getIdCellByIdStudent(note.getIdStudent(), tableEntity.getIdTable());
        if(idCellStudent <= 0){
            throw new InfrastructureException("Error buscando el identificador de la celda del estudiante en la base de datos.");
        }
        StudentCellEntity studentCellEntity = studentCellRepositoryImpl.getStudentByIdCell(idCellStudent);
        noteEntity.setStudentCell(studentCellEntity);
        TaskCellEntity taskCellEntity = taskCellRepositoryImpl.getTaskCellEntityById(note.getIdTask());
        noteEntity.setTaskCell(taskCellEntity);

        NoteCellEntity noteCellEntity = jpaNoteCellRepository.save(noteEntity);
        if(noteCellEntity == null){
            throw new InfrastructureException("Error al guardar la nota en la base de datos.");
        }

        return noteAdapterInfrastructure.toApplication(noteCellEntity);
    }

    public boolean deleteNote(int idCell){
        //borrar la nota para ese idCell en la tabla noteCellEntity
        if(idCell <= 0){
            throw new InfrastructureException("El identificador de la nota no es correcto.");
        }
        //delete note into noteCellEntity
        jpaNoteCellRepository.deleteByIdCell(idCell);
        //check if is deleted correctly if not deleted correctly return false else true
        return jpaNoteCellRepository.findByIdCell(idCell) == null;
    }
}
