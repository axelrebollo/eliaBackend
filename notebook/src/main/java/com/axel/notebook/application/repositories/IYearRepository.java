package com.axel.notebook.application.repositories;

import com.axel.notebook.domain.entities.Year;
import java.util.List;

public interface IYearRepository {

    //find all years for user and check that this year not exists
    public Boolean existYearForUser(String nameYear, int idProfile);

    //update year that user are created
    public Year updateYear(Year year);

    //get all years that one user are created
    public List<String> getAllYearsForUser(int idProfile);

    //delete year that user are created
    public void deleteYear(int idUser, int idYear);
}
