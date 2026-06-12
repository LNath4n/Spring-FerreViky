package com.ecommerce.FerreViky.exceptions.cliente;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClienteExceptionHandler {

    @ExceptionHandler(ClienteExceptions.EmailYaExisteException.class)
    public ResponseEntity<String> handleEmailYaExiste(ClienteExceptions.EmailYaExisteException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(ClienteExceptions.ClienteNoEncontradoException.class)
    public ResponseEntity<String> handleClienteNoEncontrado(ClienteExceptions.ClienteNoEncontradoException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}
