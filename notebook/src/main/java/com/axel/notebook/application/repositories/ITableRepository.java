package com.axel.notebook.application.repositories;

import com.axel.notebook.domain.entities.Table;

import java.util.List;

public interface ITableRepository {
    public List<String> getAllTablesForNameSubject(int idProfile, int idGroup);

    public Table createTable(Table table);

    public Table findTable(int idProfile, int idGroup, String nameTable);

    public boolean existTableWithClassCode(String classCode);

    public Table findTableByClassCode(String classCode);

    public boolean deleteTable(String classCode);

    public int updateNameTable(int idProfile, String nameSubject, String nameYear, String nameCourse, String nameGroup, String nameTable, String newNameTable);
}
