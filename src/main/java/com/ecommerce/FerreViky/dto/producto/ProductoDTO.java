package com.ecommerce.FerreViky.dto.producto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public class ProductoDTO {

    @Schema(description = "Datos del producto devueltos en las respuestas de la API")
    public record ProductoResponse(
            @Schema(description = "ID único del producto", example = "1")
            Long id,

            @Schema(description = "Nombre del producto", example = "Martillo de carpintero 16 oz")
            String nombreProducto,

            @Schema(description = "Marca del producto", example = "Trupper")
            String marca,

            @Schema(description = "Categoría del producto", example = "Herramientas")
            String categoria,

            @Schema(description = "Unidades disponibles en inventario", example = "50")
            Integer stock,

            @Schema(description = "Precio de lista", example = "299.99")
            BigDecimal precioNormal,

            @Schema(description = "Precio preferencial para clientes registrados", example = "249.99")
            BigDecimal precioClientes
    ) {}

    @Schema(description = "Parámetros de búsqueda avanzada. Todos son opcionales; los omitidos no se aplican al filtro.")
    public record ProductoFiltroRequest(
            @Schema(description = "Fragmento del nombre a buscar (búsqueda parcial)", example = "martillo")
            String nombre,

            @Schema(description = "Marca exacta", example = "Trupper")
            String marca,

            @Schema(description = "Categoría exacta", example = "Herramientas")
            String categoria,

            @Schema(description = "Precio mínimo (inclusive)", example = "100.00")
            BigDecimal precioMin,

            @Schema(description = "Precio máximo (inclusive)", example = "500.00")
            BigDecimal precioMax
    ) {}
}