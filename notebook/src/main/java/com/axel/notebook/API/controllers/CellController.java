package com.axel.notebook.API.controllers;

import com.axel.notebook.application.DTOs.CellResponse;
import com.axel.notebook.application.DTOs.DeleteResponse;
import com.axel.notebook.application.DTOs.UpdateResponse;
import com.axel.notebook.application.services.IManageCellUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cells")
@CrossOrigin(origins = "http://localhost:3000")
public class CellController {
    //Dependency injection
    private final IManageCellUseCase manageCellUseCase;

    //Constructor
    @Autowired
    public CellController(IManageCellUseCase manageCellUseCase) {
        this.manageCellUseCase = manageCellUseCase;
    }

    //ENDPOINTS

    //get all cells to table
    @GetMapping("/getTable")
    public ResponseEntity<?> getCell(@RequestParam String token,
                                     @RequestParam String nameSubject,
                                     @RequestParam String nameYear,
                                     @RequestParam String nameCourse,
                                     @RequestParam String nameGroup,
                                     @RequestParam String nameTable) {
        CellResponse cellsResponse = manageCellUseCase.getCellsFromTableUseCase(token, nameSubject, nameYear,
                nameCourse, nameGroup, nameTable);
        return new ResponseEntity<>(cellsResponse, HttpStatus.OK);
    }

    //add task right
    @PostMapping("/addTask")
    public ResponseEntity<?> addTask(@RequestParam String token,
                                     @RequestParam String classCode,
                                     @RequestParam String nameNewTask,
                                     @RequestParam String nameReferenceTask,
                                     @RequestParam String nameSubject,
                                     @RequestParam String nameYear,
                                     @RequestParam String nameCourse,
                                     @RequestParam String nameGroup){
        CellResponse cellResponse = manageCellUseCase.addTaskUseCase(token, classCode, nameNewTask, nameReferenceTask,
                nameSubject, nameYear, nameCourse, nameGroup);
        return new ResponseEntity<>(cellResponse, HttpStatus.OK);
    }

    //set note for student and task
    @PatchMapping("/updateNote")
    public ResponseEntity<?> updateNote(@RequestParam String token,
                                        @RequestParam String classCode,
                                        @RequestParam String student,
                                        @RequestParam String task,
                                        @RequestParam double note){
        UpdateResponse updateResponse = manageCellUseCase.updateNoteUseCase(token, classCode, student, task, note);
        return new ResponseEntity<>(updateResponse, HttpStatus.OK);
    }

    //delete task column
    @DeleteMapping("/deleteTaskColumn")
    public ResponseEntity<?> deleteTaskColumn(@RequestParam String token,
                                              @RequestParam String classCode,
                                              @RequestParam int positionTaskColumn){
        DeleteResponse deleteResponse = manageCellUseCase.deleteTaskColumnUseCase(token, classCode, positionTaskColumn);
        return new ResponseEntity<>(deleteResponse, HttpStatus.OK);
    }

    //delete student row
    @DeleteMapping("/deleteStudentTable")
    public ResponseEntity<?> deleteStudentTable(@RequestParam String token,
                                                @RequestParam String classCode,
                                                @RequestParam String nameStudent){
        DeleteResponse deleteResponse = manageCellUseCase.deleteStudentTableUseCase(token, classCode, nameStudent);
        return new ResponseEntity<>(deleteResponse, HttpStatus.OK);
    }

    @PatchMapping("/updateNameTask")
    public ResponseEntity<?> updateNameTask(@RequestParam String token,
                                            @RequestParam String classCode,
                                            @RequestParam int positionTaskColumn,
                                            @RequestParam String nameNewTask){
        UpdateResponse updateResponse = manageCellUseCase.updateNameTask(token, classCode, positionTaskColumn, nameNewTask);
        return new ResponseEntity<>(updateResponse, HttpStatus.OK);
    }

    @PatchMapping("/moveTaskLeft")
    public ResponseEntity<?> moveTaskLeft(@RequestParam String token,
                                          @RequestParam String classCode,
                                          @RequestParam int positionTaskColumn){
        UpdateResponse updateResponse = manageCellUseCase.moveTaskLeftUseCase(token, classCode, positionTaskColumn);
        return new ResponseEntity<>(updateResponse, HttpStatus.OK);
    }

    @PatchMapping("/moveTaskRight")
    public ResponseEntity<?> moveTaskRight(@RequestParam String token,
                                           @RequestParam String classCode,
                                           @RequestParam int positionTaskColumn){
        UpdateResponse updateResponse = manageCellUseCase.moveTaskRightUseCase(token, classCode, positionTaskColumn);
        return new ResponseEntity<>(updateResponse, HttpStatus.OK);
    }
}