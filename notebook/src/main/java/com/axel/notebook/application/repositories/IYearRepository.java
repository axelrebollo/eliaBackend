package com.axel.notebook.application.repositories;

public interface IYearRepository {
    //get all years that one user are created
    public void getAllYears(int idUser);

    //update year that user are created
    public void updateYear(int idUser, int idYear, String nameYear);

    //delete year that user are created
    public void deleteYear(int idUser, int idYear);
}
