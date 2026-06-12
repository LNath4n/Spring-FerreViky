package com.ecommerce.FerreViky.exceptions.carrito;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CarritoExceptionHandler {

    @ExceptionHandler(CarritoExceptions.CarritoNoEncontrado.class)
    public ResponseEntity<String> handleCarritoNoEncontrado(CarritoExceptions.CarritoNoEncontrado ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(CarritoExceptions.CarritoDeClienteNoEncontrado.class)
    public ResponseEntity<String> handleCarritoDeClienteNoEncontrado(CarritoExceptions.CarritoDeClienteNoEncontrado ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(CarritoExceptions.CantidadExcedidaException.class)
    public ResponseEntity<String> handleCantidadExcedida(CarritoExceptions.CantidadExcedidaException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(CarritoExceptions.CantidadNoValida.class)
    public ResponseEntity<String> handleCantidadNoValida(CarritoExceptions.CantidadNoValida ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(CarritoExceptions.ProductoNoEncontrado.class)
    public ResponseEntity<String> handleCantidadNoValida(CarritoExceptions.ProductoNoEncontrado ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(CarritoExceptions.ProductoYaEnCarritoException.class)
    public ResponseEntity<String> handleProductoYaEnCarrito(CarritoExceptions.ProductoYaEnCarritoException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(CarritoExceptions.ProductoNoEnCarritoException.class)
    public ResponseEntity<String> handleProductoNoEnCarrito(CarritoExceptions.ProductoNoEnCarritoException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(CarritoExceptions.ErrorAlCrearCarritoException.class)
    public ResponseEntity<String> handleErrorAlCrearCarritoException(CarritoExceptions.ErrorAlCrearCarritoException ex){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }
}
