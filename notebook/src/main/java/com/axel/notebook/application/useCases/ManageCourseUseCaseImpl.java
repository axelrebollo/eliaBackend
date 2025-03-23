package com.axel.notebook.application.useCases;

import com.axel.notebook.application.DTOs.CourseResponse;
import com.axel.notebook.application.exceptions.ApplicationException;
import com.axel.notebook.application.repositories.ICourseRepository;
import com.axel.notebook.application.repositories.IYearRepository;
import com.axel.notebook.application.services.IManageCourseUseCase;
import com.axel.notebook.application.services.producers.ICourseProducer;
import com.axel.notebook.domain.entities.Course;
import com.axel.notebook.domain.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManageCourseUseCaseImpl implements IManageCourseUseCase {
    //Dependency injection
    private final ICourseProducer courseProducer;   //infrastructure layer
    private final ICourseRepository courseRepository;   //infrastructure layer
    private final CourseService courseService;  //domain layer
    private final IYearRepository yearRepository;   //infrastructure layer

    //Constructor
    @Autowired
    public ManageCourseUseCaseImpl(ICourseProducer courseProducer,
                                   ICourseRepository courseRepository,
                                   IYearRepository yearRepository) {
        this.courseProducer = courseProducer;
        this.courseRepository = courseRepository;
        this.courseService = new CourseService();
        this.yearRepository = yearRepository;
    }

    //get all courses with token from one year
    public CourseResponse getAllCoursesUseCase(String token, String nameYear) {
        //check data
        if(token == null || token.isEmpty()){
            throw new ApplicationException("Error el token está vacío.");
        }

        //decode token and get idProfile
        int idProfile = getProfileId(token);

        if(idProfile <= 0){
            throw new ApplicationException("Error al recuperar los usuarios, el perfil no existe");
        }

        //if user not selected year, returns empty list
        if(nameYear.isEmpty()){
            List<String> emptyCourses = new ArrayList<String>();
            return new CourseResponse(emptyCourses);
        }

        //if user select year
        return new CourseResponse(getAllCoursesForUser(idProfile, nameYear));
    }

    private int getProfileId(String token) {
        return courseProducer.sendToken(token);
    }

    //Get all subjects for one user
    private List<String> getAllCoursesForUser(int idProfile, String nameYear) {
        if(idProfile <= 0){
            throw new ApplicationException("El usuario no existe, no se ha encontrado el perfil.");
        }

        if(nameYear.isEmpty()){
            throw new ApplicationException("El nombre del año es nulo");
        }

        return courseRepository.getAllCoursesForUser(idProfile, nameYear);
    }

    //create a new course into year selected
    public CourseResponse addCourseUseCase(String token, String nameCourse, String nameYear){
        //check data
        if(token == null || token.isEmpty()){
            throw new ApplicationException("El token está vacío o es nulo");
        }

        if(nameCourse == null || nameCourse.isEmpty()){
            throw new ApplicationException("El nombre del curso está vacío.");
        }

        if(nameYear == null || nameYear.isEmpty()){
            throw new ApplicationException("Se debe seleccionar un año.");
        }

        //check token and extract idProfile
        int idProfile = getProfileId(token);

        //Check if user exist into system
        if(idProfile <= 0){
            throw new ApplicationException("No se ha encontrado el perfil del usuario, el usuario no existe");
        }

        //Find all years for user and check if exists year into system for this user
        if(!yearRepository.existYearForUser(nameYear, idProfile)){
            throw new ApplicationException("El año seleccionado no existe para este usuario.");
        }

        //find for name year to id year
        int idYear = yearRepository.getIdYearForUser(idProfile, nameYear);

        if(idYear <= 0){
            throw new ApplicationException("Problema en la base de datos con el id del año. No se han encontrado años.");
        }

        if(courseRepository.existCourseForUser(idYear, nameCourse)){
            throw new ApplicationException("El curso existe en el sistema.");
        }

        //create new course
        Course newCourse = courseService.addCourse(nameCourse, idProfile, idYear);

        //add course
        try{
            newCourse = courseRepository.updateCourse(newCourse);
        }
        catch(ApplicationException e){
            throw new ApplicationException("Error al crear el curso.");
        }

        List<String> courseToYear = null;

        //retornar la lista de cursos
        if(newCourse != null){
            courseToYear = getAllCoursesForUser(idProfile, nameYear);
        }

        return new CourseResponse(courseToYear);
    }
}
