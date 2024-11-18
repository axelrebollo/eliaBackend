package com.axel.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerRESTuserTemporal {
    @GetMapping("/hello")
    public String sayHello() {
        return "¡Hola desde Spring Boot!";
    }
}
