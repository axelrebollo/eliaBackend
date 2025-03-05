package com.axel.notebook.application.services;

public interface IManageYearUseCase {
    //get all years
    public void getAllYearsUseCase();

    //create a new year
    public void addYearUseCase();

    //update name year
    public void updateYearUseCase();

    //delete all information about this year
    public void deleteYearUseCase();
}
