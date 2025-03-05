package com.axel.notebook.infrastructure.persistence;

import com.axel.notebook.infrastructure.JpaEntities.YearEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaYearRepository extends JpaRepository<YearEntity, Integer> {
    //Find all years
    public List<YearEntity> findAll();

    //Create year
    public YearEntity save(YearEntity year);

    //Delete year
    public void deleteById(Integer idYear);
}
