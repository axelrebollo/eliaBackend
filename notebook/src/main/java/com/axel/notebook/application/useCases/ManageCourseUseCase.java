package com.axel.notebook.application.useCases;

import com.axel.notebook.application.DTOs.CourseResponse;
import com.axel.notebook.application.exceptions.ApplicationException;
import com.axel.notebook.application.repositories.ICourseRepository;
import com.axel.notebook.application.services.IManageCourseUseCase;
import com.axel.notebook.application.services.producers.ICourseProducer;
import com.axel.notebook.domain.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManageCourseUseCase implements IManageCourseUseCase {
    //Dependency injection
    ICourseProducer courseProducer;
    ICourseRepository courseRepository;
    CourseService courseService;

    //Constructor
    @Autowired
    public ManageCourseUseCase(ICourseProducer courseProducer,
                               ICourseRepository courseRepository,
                               CourseService courseService) {
        this.courseProducer = courseProducer;
        this.courseRepository = courseRepository;
        this.courseService = courseService;
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

    public int getProfileId(String token) {
        return courseProducer.sendToken(token);
    }

    //Get all subjects for one user
    public List<String> getAllCoursesForUser(int idProfile, String nameYear) {
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
        //TODO
        return null;
    }
}
