package com.axel.notebook.domain.services;

import com.axel.notebook.domain.entities.Subject;

public class SubjectService {
    public Subject addSubject(String nameSubject, int idProfile){
        return new Subject(nameSubject, idProfile);
    }
}
