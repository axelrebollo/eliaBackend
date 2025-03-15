package com.axel.notebook.application.repositories;

import com.axel.notebook.domain.entities.Year;
import java.util.List;

public interface IYearRepository {

    //find all years for user and check that this year not exists
    public boolean existYearForUser(String nameYear, int idProfile);

    //update year that user are created
    public Year updateYear(Year year);

    //get all years that one user are created
    public List<String> getAllYearsForUser(int idProfile);

    //get id year for one user and one name year
    public int getIdYear(int idProfile, String nameYear);

    //delete year that user are created
    public void deleteYear(int idUser, int idYear);
}
