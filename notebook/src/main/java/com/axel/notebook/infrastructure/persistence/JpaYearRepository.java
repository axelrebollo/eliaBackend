package com.axel.notebook.infrastructure.persistence;

import com.axel.notebook.infrastructure.JpaEntities.YearEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaYearRepository extends JpaRepository<YearEntity, Integer> {
    //Find all years
    public List<YearEntity> findAll();

    //Find a year from name
    public List<YearEntity> findByIdProfile(int idProfile);

    //Create year
    public YearEntity save(YearEntity year);

    //Delete year
    public void deleteById(Integer idYear);

    //find by id
    public YearEntity findByIdYear(int idYear);

    @Query("SELECT y FROM YearEntity y WHERE y.idProfile = :idProfile AND y.nameYear = :name")
    public YearEntity findByNameAndIdProfile(String name, int idProfile);
}
