package com.ecommerce.FerreViky.dto.producto;

import java.math.BigDecimal;

public record ProductoFiltroRequest(
        String nombre,
        String marca,
        String categoria,
        BigDecimal precioMin,
        BigDecimal precioMax
) {}