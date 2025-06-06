package com.axel.notebook.application.useCases;

import com.axel.notebook.application.DTOs.DeleteResponse;
import com.axel.notebook.application.DTOs.UpdateResponse;
import com.axel.notebook.domain.entities.Year;
import com.axel.notebook.application.DTOs.YearResponse;
import com.axel.notebook.application.exceptions.ApplicationException;
import com.axel.notebook.application.repositories.IYearRepository;
import com.axel.notebook.application.services.IManageYearUseCase;
import com.axel.notebook.application.services.producers.IYearProducer;
import com.axel.notebook.domain.services.YearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ManageYearUseCaseImpl implements IManageYearUseCase {
    //Dependency injection
    private final IYearRepository yearRepository; //infrastructure layer
    private final IYearProducer yearProducer; //infrastructure layer
    private final YearService yearService;    //domain layer

    //Constructor
    @Autowired
    public ManageYearUseCaseImpl(IYearRepository yearRepository,
                                 IYearProducer yearProducer) {
        this.yearRepository = yearRepository;
        this.yearProducer = yearProducer;
        this.yearService = new YearService();
    }

    //create a new year
    public YearResponse addYearUseCase(String token, String nameYear) {
        //check data
        if(token == null || token.isEmpty()){
            throw new ApplicationException("Ha habido un problema con el registro de usuario.");
        }

        if(nameYear == null || nameYear.isEmpty()){
            throw new ApplicationException("El nombre del año está vacío o no existe.");
        }

        //Check if token is correct and returns decoded
        int idProfile = getProfileId(token);

        //Check if user exist into system
        if(idProfile <= 0){
            throw new ApplicationException("No se ha encontrado el perfil del usuario, el usuario no existe");
        }

        //Check if exist year for this user
        if(yearRepository.existYearForUser(nameYear, idProfile)){
            throw new ApplicationException("Existe un año con ese nombre con este usuario.");
        }

        //Create year
        Year newYear = yearService.addYear(nameYear,idProfile);

        //Save year
        try{
            newYear = yearRepository.updateYear(newYear);
        }
        catch(ApplicationException e){
            throw new ApplicationException("Error al crear el año.");
        }

        //return the list of years for this user
        List<String> yearsToUser = null;

        if(newYear != null){
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

    private int getProfileId(String token) {
        return yearProducer.sendToken(token);
    }

    //Get all years for one user
    private List<String> getAllYearsForUser(int idProfile){
        if(idProfile <= 0){
            throw new ApplicationException("El usuario no existe, no se ha encontrado el perfil.");
        }
        return yearRepository.getAllYearsNameForUser(idProfile);
    }

    //delete all information about this year
    public DeleteResponse deleteYearUseCase(String token, String nameYear) {
        if(token == null || token.isEmpty() || nameYear == null || nameYear.isEmpty()){
            throw new ApplicationException("Algún dato no es correcto para borrar el año correctamente.");
        }

        //decode token and get idProfile
        int idProfile = getProfileId(token);

        if(idProfile <= 0){
            throw new ApplicationException("Error al recuperar los usuarios, el perfil no existe");
        }

        boolean idDeleted = false;
        idDeleted = yearRepository.deleteYear(idProfile,nameYear);

        return new DeleteResponse(idDeleted, "El año se ha borrado correctamente.");
    }

    //update name year
    public UpdateResponse updateYearUseCase(String token, String nameYear, String newNameYear){
        if(token == null || token.isEmpty() || nameYear == null || nameYear.isEmpty() || newNameYear == null || newNameYear.isEmpty()){
            throw new ApplicationException("Algún dato no es correcto para actualizar el nombre del año.");
        }

        //decode token and get idProfile
        int idProfile = getProfileId(token);

        if(idProfile <= 0){
            throw new ApplicationException("Error al recuperar los usuarios, el perfil no existe");
        }

        int idYear = yearRepository.updateNameYear(idProfile, nameYear, newNameYear);
        if(idYear <= 0){
            throw new ApplicationException("El nombre del año no se ha actualizado correctamente.");
        }

        return new UpdateResponse(idYear);
    }
}
