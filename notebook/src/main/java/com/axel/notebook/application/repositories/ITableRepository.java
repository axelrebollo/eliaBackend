package com.axel.notebook.application.repositories;

import java.util.List;

public interface ITableRepository {
    public List<String> getAllTablesForNameSubject(int idProfile, int idGroup);
}
