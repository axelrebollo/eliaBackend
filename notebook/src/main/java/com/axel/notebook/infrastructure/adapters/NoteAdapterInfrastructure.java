package com.axel.notebook.infrastructure.adapters;

import com.axel.notebook.domain.entities.Table;
import com.axel.notebook.domain.valueObjects.Note;
import com.axel.notebook.infrastructure.JpaEntities.NoteCellEntity;
import com.axel.notebook.infrastructure.JpaEntities.TableEntity;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaNoteCellRepository;
import com.axel.notebook.infrastructure.persistence.JpaTableRepository;
import org.springframework.stereotype.Service;

@Service
public class NoteAdapterInfrastructure {
    //Dependency injection
    private final JpaNoteCellRepository jpaNoteCellRepository;
    private final JpaTableRepository jpaTableRepository;

    //Constructor
    public NoteAdapterInfrastructure(JpaNoteCellRepository jpaNoteCellRepository, JpaTableRepository jpaTableRepository) {
        this.jpaNoteCellRepository = jpaNoteCellRepository;
        this.jpaTableRepository = jpaTableRepository;
    }

    public Note toApplication(NoteCellEntity noteCellEntity) {
        if(noteCellEntity == null){
            throw new InfrastructureException("La entidad de infrastructura está vacía.");
        }
        return new Note(noteCellEntity.getIdCell(), noteCellEntity.getNote(),
                noteCellEntity.getPositionRow(), noteCellEntity.getPositionCol(),
                noteCellEntity.getStudentCell().getIdCell(), noteCellEntity.getTaskCell().getIdCell());
    }

    public NoteCellEntity toApplication(Note note) {
        if(note == null){
            throw new InfrastructureException("La entidad de aplicación está vacía.");
        }
        return jpaNoteCellRepository.findByIdCell(note.getIdNote());
    }

    public NoteCellEntity toApplicationWithoutId(Note note, Table table) {
        if(note == null){
            throw new InfrastructureException("La entidad de aplicación está vacía.");
        }

        TableEntity tableEntity = jpaTableRepository.findById(table.getIdTable());
        if(tableEntity == null){
            throw new InfrastructureException("Error con la tabla.");
        }

        return new NoteCellEntity(tableEntity, note.getPositionRow(), note.getPositionCol(), note.getNote());
    }
}
