package com.axel.notebook.application.services;

import com.axel.notebook.application.DTOs.DeleteResponse;
import com.axel.notebook.application.DTOs.YearResponse;

public interface IManageYearUseCase {
    //get all years
    public YearResponse getAllYearsFromTokenUseCase(String token);

    //create a new year
    public YearResponse addYearUseCase(String token, String nameYear);

    //update name year
    public void updateYearUseCase();

    //delete all information about this year
    public DeleteResponse deleteYearUseCase(String token, String nameYear);
}
