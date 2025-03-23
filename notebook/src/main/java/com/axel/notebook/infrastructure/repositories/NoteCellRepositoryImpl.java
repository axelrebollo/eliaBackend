package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.INoteCellRepository;
import com.axel.notebook.domain.valueObjects.Note;
import com.axel.notebook.infrastructure.JpaEntities.NoteCellEntity;
import com.axel.notebook.infrastructure.adapters.NoteAdapterInfrastructure;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaNoteCellRepository;
import org.springframework.stereotype.Repository;

@Repository
public class NoteCellRepositoryImpl implements INoteCellRepository {
    //Dependency injection
    private final JpaNoteCellRepository jpaNoteCellRepository;
    private final NoteAdapterInfrastructure noteAdapterInfrastructure;

    //Constructor
    public NoteCellRepositoryImpl(JpaNoteCellRepository jpaNoteCellRepository,
                                  NoteAdapterInfrastructure noteAdapterInfrastructure) {
        this.jpaNoteCellRepository = jpaNoteCellRepository;
        this.noteAdapterInfrastructure = noteAdapterInfrastructure;
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
}
