package com.axel.notebook.infrastructure.persistence;

import com.axel.notebook.infrastructure.JpaEntities.NoteCellEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaNoteCellRepository extends JpaRepository<NoteCellEntity, Integer> {
    public NoteCellEntity findByIdCell(int idCell);
}
