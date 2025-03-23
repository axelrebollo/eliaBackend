package com.axel.notebook.application.repositories;

import com.axel.notebook.domain.valueObjects.Note;

public interface INoteCellRepository {
    public Note getNoteForId(int idNote);
}
