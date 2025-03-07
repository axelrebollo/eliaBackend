package com.axel.notebook.application.useCases;

import com.axel.notebook.application.DTOs.YearApplication;
import com.axel.notebook.application.DTOs.YearResponse;
import com.axel.notebook.application.exceptions.ApplicationException;
import com.axel.notebook.application.repositories.IYearRepository;
import com.axel.notebook.application.services.IManageYearUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageYearUseCaseImpl implements IManageYearUseCase {

    //Dependency injection
    private IYearRepository yearRepository;

    //Constructor
    @Autowired
    public ManageYearUseCaseImpl(IYearRepository yearRepository) {
        this.yearRepository = yearRepository;
    }

    //create a new year
    public YearResponse addYearUseCase(String token, String nameYear) {
        if(token == null || token.isEmpty()){
            throw new ApplicationException("Ha habido un problema con el registro de usuario.");
        }

        if(nameYear == null || nameYear.isEmpty()){
            throw new ApplicationException("El nombre del año está vacío o no existe.");
        }

        int idProfile = 0;
        //Check if token is correct and returns decoded
            //kafka petition
        //TODO

        //Check if year exist into system for this user
        if(idProfile <= 0){
            throw new ApplicationException("No se ha encontrado el perfil del usuario, el usuario no existe");
        }

        boolean existYearInDB = yearRepository.existYearForUser(nameYear, idProfile);

        if(existYearInDB){
            throw new ApplicationException("Existe un año con ese nombre con este usuario.");
        }

        //Create year into database
        YearApplication newYearApplication = new YearApplication(nameYear,idProfile);

        try{
            newYearApplication = yearRepository.updateYear(newYearApplication);
        }
        catch(ApplicationException e){
            throw new ApplicationException("Error al crear el año.");
        }

        //return the list of years for this user
        List<String> yearsToUser = null;

        if(newYearApplication != null){
            yearsToUser = getAllYearsForUser(idProfile);
        }

        return new YearResponse(yearsToUser);
    }

    //Get all years for user from token
    public YearResponse getAllYearsFromTokenUseCase(String token) {
        if(token == null || token.isEmpty()){
            throw new ApplicationException("Error el token está vacío.");
        }
        //decode token
        //TODO
        int idProfile = 0;

        return new YearResponse(getAllYearsForUser(idProfile));
    }

    //Get all years for one user
    public List<String> getAllYearsForUser(int idProfile){
        if(idProfile <= 0){
            throw new ApplicationException("El usuario no existe, no se ha encontrado el perfil.");
        }
        return yearRepository.getAllYearsForUser(idProfile);
    }

    //update name year
    public void updateYearUseCase() {
        //TODO
    }

    //delete all information about this year
    public void deleteYearUseCase() {
        //delete in cascade
        //TODO
    }
}
