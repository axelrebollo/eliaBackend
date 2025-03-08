package com.axel.notebook.application.useCases;

import com.axel.notebook.application.DTOs.YearApplication;
import com.axel.notebook.application.DTOs.YearResponse;
import com.axel.notebook.application.exceptions.ApplicationException;
import com.axel.notebook.application.repositories.IYearRepository;
import com.axel.notebook.application.services.IManageYearUseCase;
import com.axel.notebook.application.services.IYearConsumer;
import com.axel.notebook.application.services.IYearProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ManageYearUseCaseImpl implements IManageYearUseCase {

    //Dependency injection
    private IYearRepository yearRepository;
    private IYearProducer yearProducer;
    private IYearConsumer yearConsumer;

    //Constructor
    @Autowired
    public ManageYearUseCaseImpl(IYearRepository yearRepository, IYearProducer yearProducer, IYearConsumer yearConsumer) {
        this.yearRepository = yearRepository;
        this.yearProducer = yearProducer;
        this.yearConsumer = yearConsumer;
    }

    //create a new year
    public YearResponse addYearUseCase(String token, String nameYear) {
        if(token == null || token.isEmpty()){
            throw new ApplicationException("Ha habido un problema con el registro de usuario.");
        }

        if(nameYear == null || nameYear.isEmpty()){
            throw new ApplicationException("El nombre del año está vacío o no existe.");
        }

        //Check if token is correct and returns decoded
        int idProfile = getProfileId(token);

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
        int idProfile = getProfileId(token);

        return new YearResponse(getAllYearsForUser(idProfile));
    }

    //Get all years for one user
    public List<String> getAllYearsForUser(int idProfile){
        if(idProfile <= 0){
            throw new ApplicationException("El usuario no existe, no se ha encontrado el perfil.");
        }
        return yearRepository.getAllYearsForUser(idProfile);
    }

    public int getProfileId(String token) {
        CompletableFuture<Integer> future = yearConsumer.createFuture(token);
        yearProducer.sendToken(token);
        return future.join(); // Espera la respuesta
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
