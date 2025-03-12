package com.axel.notebook.domain.services;

import com.axel.notebook.domain.entities.Subject;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
    public Subject addSubject(String nameSubject, int idProfile){
        return new Subject(nameSubject, idProfile);
    }
}
