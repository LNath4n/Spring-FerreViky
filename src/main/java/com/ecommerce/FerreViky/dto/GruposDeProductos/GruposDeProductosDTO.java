package com.ecommerce.FerreViky.dto.GruposDeProductos;


import java.util.List;

public class GruposDeProductosDTO {

    // Lo que ve el público: solo nombre y los productos del grupo
    public record GrupoPublicoResponse(
            Long id,
            String nombre,
            List<Long> productoIds
    ) {}

    // Lo que ve el admin: todo excepto palabrasComunes sigue siendo discutible,
    // pero prefijoClave sí tiene sentido para administrar
    public record GrupoAdminResponse(
            Long id,
            String nombre,
            String prefijoClave,
            String palabrasComunes,
            List<Long> productoIds
    ) {}
}
