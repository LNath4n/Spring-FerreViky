package com.ecommerce.FerreViky.dto.carrito;

public class CarritoDTO {

    public record AgregarCarrito(Long idCliente, Long idProducto,Integer cantidad) {
    }
}
