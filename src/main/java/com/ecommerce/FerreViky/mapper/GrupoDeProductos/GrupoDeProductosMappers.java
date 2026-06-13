package com.ecommerce.FerreViky.mapper.GrupoDeProductos;

import com.ecommerce.FerreViky.dto.GruposDeProductos.GruposDeProductosDTO.GrupoPublicoResponse;
import com.ecommerce.FerreViky.dto.GruposDeProductos.GruposDeProductosDTO.GrupoAdminResponse;
import com.ecommerce.FerreViky.models.GrupoDeProductos;
import com.ecommerce.FerreViky.models.Producto;

import java.util.Collections;
import java.util.List;

public class GrupoDeProductosMappers {

    public static GrupoPublicoResponse toPublicoResponse(GrupoDeProductos grupo) {
        List<Long> ids = grupo.getEstilos() != null
                ? grupo.getEstilos().stream().map(Producto::getId).toList()
                : Collections.emptyList();

        return new GrupoPublicoResponse(
                grupo.getId(),
                grupo.getNombre(),
                ids
        );
    }

    public static GrupoAdminResponse toAdminResponse(GrupoDeProductos grupo) {
        List<Long> ids = grupo.getEstilos() != null
                ? grupo.getEstilos().stream().map(Producto::getId).toList()
                : Collections.emptyList();

        return new GrupoAdminResponse(
                grupo.getId(),
                grupo.getNombre(),
                grupo.getPrefijoClave(),
                grupo.getPalabrasComunes(),
                ids
        );
    }
}