package com.axel.notebook.domain.services;

import com.axel.notebook.domain.entities.Table;

public class TableService {
    public Table addTable(String nameTable, int idTeacher, int idGroup){
        return new Table(nameTable, idTeacher, idGroup);
    }
}
