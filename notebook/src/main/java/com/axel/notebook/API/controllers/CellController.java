package com.axel.notebook.API.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cells")
@CrossOrigin(origins = "http://localhost:3000")
public class CellController {

    //endpoints
    @PostMapping("/addCell")
    public ResponseEntity<?> addCell() {
        //TODO
        return null;
    }

    @GetMapping("/getCell")
    public ResponseEntity<?> getCell() {
        //TODO
        return null;
    }

    @PutMapping("/updateCell")
    public ResponseEntity<?> updateCell() {
        //TODO
        return null;
    }
/*
    @PatchMapping("/updateNameCell")
    public ResponseEntity<?> updateNameCell() {
        return null;
    }
*/
    @DeleteMapping("/deleteCell")
    public ResponseEntity<?> deleteCell() {
        //TODO
        return null;
    }
}
