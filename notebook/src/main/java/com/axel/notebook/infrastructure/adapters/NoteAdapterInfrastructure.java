package com.axel.notebook.infrastructure.adapters;

import com.axel.notebook.domain.valueObjects.Note;
import com.axel.notebook.infrastructure.JpaEntities.NoteCellEntity;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaNoteCellRepository;
import org.springframework.stereotype.Service;

@Service
public class NoteAdapterInfrastructure {
    //Dependency injection
    private final JpaNoteCellRepository jpaNoteCellRepository;

    //Constructor
    public NoteAdapterInfrastructure(JpaNoteCellRepository jpaNoteCellRepository) {
        this.jpaNoteCellRepository = jpaNoteCellRepository;
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
}
