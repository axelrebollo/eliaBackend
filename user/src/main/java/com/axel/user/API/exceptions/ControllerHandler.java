package com.axel.user.API.exceptions;

import com.axel.user.application.exceptions.ApplicationException;
import com.axel.user.domain.exceptions.DomainException;
import com.axel.user.infrastructure.exceptions.InfrastructureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, Object>> manejarExcepcionDominio(DomainException e) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, "DOMAIN_ERROR", e.getMessage());
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Map<String, Object>> manejarExcepcionAplicacion(ApplicationException e) {
        return construirRespuesta(HttpStatus.CONFLICT, "APPLICATION_ERROR", e.getMessage());
    }

    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<Map<String, Object>> manejarExcepcionInfraestructura(InfrastructureException e) {
        return construirRespuesta(HttpStatus.INTERNAL_SERVER_ERROR, "INFRASTRUCTURE_ERROR", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarExcepcionGeneral(Exception e) {
        return construirRespuesta(HttpStatus.INTERNAL_SERVER_ERROR, "UNKNOWN_ERROR", "Ha ocurrido un error inesperado.");
    }

    private ResponseEntity<Map<String, Object>> construirRespuesta(HttpStatus status, String codigo, String mensaje) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("codigo", codigo);
        errorResponse.put("mensaje", mensaje);
        errorResponse.put("status", status.value());

        return ResponseEntity.status(status).body(errorResponse);
    }
}
