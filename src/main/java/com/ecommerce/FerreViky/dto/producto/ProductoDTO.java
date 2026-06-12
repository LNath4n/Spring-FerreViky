package com.ecommerce.FerreViky.dto.producto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public class ProductoDTO {

    public record ProductoPublicoResponse(
            Long id,
            String codigo,
            String descripcion,
            String unidad,
            String marca,
            BigDecimal precioPublicoIva
    ) {}

    public record ProductoAdminResponse(
            Long id,
            String codigo,
            String clave,
            String descripcion,
            String margenMercado,
            String caja,
            String master,
            String unidad,
            String ean,
            BigDecimal precioMayoreoIva,
            BigDecimal precioDistribuidorIva,
            BigDecimal precioPublicoIva,
            String marca,
            Long grupoDeProductosId
    ) {}
}