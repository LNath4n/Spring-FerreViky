package com.ecommerce.FerreViky.mapper.carrito;


import com.ecommerce.FerreViky.dto.carrito.CarritoDTO;
import com.ecommerce.FerreViky.models.Carrito;
import com.ecommerce.FerreViky.models.CarritoProducto;

import java.math.BigDecimal;
import java.util.List;

public class CarritoMappers {

    public static CarritoDTO.CarritoProductoDTO toCarritoProductoDTO(CarritoProducto cp) {
        return new CarritoDTO.CarritoProductoDTO(
                cp.getProducto().getId(),
                cp.getProducto().getDescripcion(),
                cp.getProducto().getClave(),
                cp.getCantidad(),
                cp.getProducto().getPrecioPublicoIva()
        );
    }

    public static CarritoDTO.CarritoResponseDTO toCarritoResponseDTO(Carrito carrito) {
        List<CarritoDTO.CarritoProductoDTO> productos = carrito.getProductos().stream()
                .map(CarritoMappers::toCarritoProductoDTO)
                .toList();

        BigDecimal subtotal = productos.stream()
                .map(p -> p.precioPublicoIva().multiply(BigDecimal.valueOf(p.cantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CarritoDTO.CarritoResponseDTO(
                carrito.getId(),
                carrito.getCliente().getEmail(),
                productos,
                carrito.getFechaCreacion(),
                subtotal
        );
    }
}
