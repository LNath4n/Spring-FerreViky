package com.ecommerce.FerreViky.mapper.Producto;

import com.ecommerce.FerreViky.dto.producto.ProductoDTO.ProductoResponse;
import com.ecommerce.FerreViky.models.Producto;

public class ProductoMappers {

    // Convierte una entidad Producto a su DTO de respuesta
    public static ProductoResponse toResponse(Producto producto) {
        return new ProductoResponse(
                producto.getId(),
                producto.getNombreProducto(),
                producto.getMarca(),
                producto.getCategoria(),
                producto.getStock(),
                producto.getPrecioNormal(),
                producto.getPrecioClientes() // ojo: en el modelo es precioClientes, en el record es precioCliente
        );
    }
}