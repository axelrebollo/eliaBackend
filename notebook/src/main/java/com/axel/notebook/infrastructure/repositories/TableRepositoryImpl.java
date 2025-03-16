package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.ITableRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TableRepositoryImpl implements ITableRepository {

    public List<String> getAllTablesForNameSubject(int idProfile, int idGroup){
        return null;
    }
}
