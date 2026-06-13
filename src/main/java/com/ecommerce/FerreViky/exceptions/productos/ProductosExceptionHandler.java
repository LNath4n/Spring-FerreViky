package com.ecommerce.FerreViky.exceptions.productos;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductosExceptionHandler {

    @ExceptionHandler(ProductosExceptions.ProductoNoEncontradoException.class)
    public ResponseEntity<String> handleProductoNoEncontrado(ProductosExceptions.ProductoNoEncontradoException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(ProductosExceptions.GrupoNoEncontradoException.class)
    public ResponseEntity<String> handleGrupoDeProductoNoEncontrado(ProductosExceptions.GrupoNoEncontradoException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}
