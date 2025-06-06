package com.axel.notebook.API.controllers;

import com.axel.notebook.application.DTOs.DeleteResponse;
import com.axel.notebook.application.DTOs.GroupResponse;
import com.axel.notebook.application.DTOs.UpdateResponse;
import com.axel.notebook.application.services.IManageGroupUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
@CrossOrigin(origins = "http://localhost:3000")
public class GroupController {
    //dependency injection
    private final IManageGroupUseCase manageGroupUseCase;

    //Constructor
    @Autowired
    public GroupController(final IManageGroupUseCase manageGroupUseCase) {
        this.manageGroupUseCase = manageGroupUseCase;
    }

    //ENDPOINTS

    //add group to subject, year, course
    @PostMapping("/addGroup")
    public ResponseEntity<?> addGroup(@RequestParam String token,
                                      @RequestParam String nameCourse,
                                      @RequestParam String nameSubject,
                                      @RequestParam String nameYear,
                                      @RequestParam String nameGroup) {
        GroupResponse groupResponse = manageGroupUseCase.addGroupUseCase(token, nameCourse, nameSubject, nameYear, nameGroup);
        return new ResponseEntity<>(groupResponse, HttpStatus.OK);
    }

    //get group
    @GetMapping("/getGroup")
    public ResponseEntity<?> getGroups(@RequestParam String token,
                                       @RequestParam String nameCourse,
                                       @RequestParam String nameSubject,
                                       @RequestParam String nameYear) {
        GroupResponse groupResponse = manageGroupUseCase.getAllGroupsUseCase(token, nameCourse, nameSubject, nameYear);
        return new ResponseEntity<>(groupResponse, HttpStatus.OK);
    }

    @DeleteMapping("/deleteGroup")
    public ResponseEntity<?> deleteGroup(@RequestParam String token,
                                         @RequestParam String nameCourse,
                                         @RequestParam String nameSubject,
                                         @RequestParam String nameYear,
                                         @RequestParam String nameGroup) {
        DeleteResponse deleteResponse = manageGroupUseCase.deleteGroupUseCase(token, nameCourse, nameSubject, nameYear, nameGroup);
        return new ResponseEntity<>(deleteResponse, HttpStatus.OK);
    }

    @PatchMapping("/updateNameGroup")
    public ResponseEntity<?> updateNameGroup(@RequestParam String token,
                                             @RequestParam String nameSubject,
                                             @RequestParam String nameYear,
                                             @RequestParam String nameCourse,
                                             @RequestParam String nameGroup,
                                             @RequestParam String newNameGroup) {
        UpdateResponse updateResponse = manageGroupUseCase.updateGroupUseCase(token, nameSubject, nameYear, nameCourse, nameGroup, newNameGroup);
        return new ResponseEntity<>(updateResponse, HttpStatus.OK);
    }
}
