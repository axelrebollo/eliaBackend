package com.axel.user.API.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerRESTuserTemporal {
    @GetMapping("/hello")
    public String sayHello() {
        return "¡Hola desde Spring Boot desde tu controlador en el microservicio user!";
    }
}
