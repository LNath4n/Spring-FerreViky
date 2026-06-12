package com.ecommerce.FerreViky.exceptions.productos;

public class ProductosExceptions {

    public static class ProductoNoEncontradoException extends RuntimeException {
        public ProductoNoEncontradoException(Long id) {
            super("No se encontró producto con id : " + id);
        }
    }
}
