package com.ecommerce.FerreViky.mapper.Producto;

import com.ecommerce.FerreViky.dto.producto.ProductoDTO.ProductoPublicoResponse;
import com.ecommerce.FerreViky.dto.producto.ProductoDTO.ProductoAdminResponse;
import com.ecommerce.FerreViky.models.Producto;

public class ProductoMappers {

    public static ProductoPublicoResponse toPublicoResponse(Producto producto) {
        return new ProductoPublicoResponse(
                producto.getId(),
                producto.getCodigo(),
                producto.getDescripcion(),
                producto.getUnidad(),
                producto.getMarca(),
                producto.getPrecioPublicoIva()
        );
    }

    public static ProductoAdminResponse toAdminResponse(Producto producto) {
        return new ProductoAdminResponse(
                producto.getId(),
                producto.getCodigo(),
                producto.getClave(),
                producto.getDescripcion(),
                producto.getMargenMercado(),
                producto.getCaja(),
                producto.getMaster(),
                producto.getUnidad(),
                producto.getEan(),
                producto.getPrecioMayoreoIva(),
                producto.getPrecioDistribuidorIva(),
                producto.getPrecioPublicoIva(),
                producto.getMarca(),
                producto.getGrupoDeProductos() != null ? producto.getGrupoDeProductos().getId() : null
        );
    }
}