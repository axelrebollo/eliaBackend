package com.axel.notebook.application.repositories;

import com.axel.notebook.domain.entities.Table;
import com.axel.notebook.domain.valueObjects.Note;
import com.axel.notebook.domain.valueObjects.Task;

public interface INoteCellRepository {
    public Note getNoteForId(int idNote);

    public Note addNote(Note note, Table table, Task task);
}
