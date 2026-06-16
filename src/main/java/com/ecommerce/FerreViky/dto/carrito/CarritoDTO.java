package com.ecommerce.FerreViky.dto.carrito;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CarritoDTO {

    public record AgregarCarrito(
            @Schema(description = "ID perteneciente al Cliente",example = "4")
            Long idCliente,
            @Schema(description = "ID perteneciente al Producto",example = "2")
            Long idProducto,
            @Schema(description = "Cantidad del producto",example = "1")
            Integer cantidad
    ) {}

    public record CarritoProductoDTO(
            Long productoId,
            String descripcion,
            String clave,
            Integer cantidad,
            BigDecimal precioPublicoIva
    ) {}
    public record CarritoResponseDTO(
            Long id,
            String emailCliente,
            List<CarritoProductoDTO> productos,
            LocalDateTime fechaCreacion,
            BigDecimal subtotal
    ) {}
}
