package com.ecommerce.FerreViky.excepctions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteExceptions.EmailYaExisteException.class)
    public ResponseEntity<String> handleEmailYaExiste(ClienteExceptions.EmailYaExisteException ex){
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(ClienteExceptions.ClienteNoEncontradoException.class)
    public ResponseEntity<String> handleClienteNoEncontrado(ClienteExceptions.ClienteNoEncontradoException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}