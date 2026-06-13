package com.ecommerce.FerreViky.repository;

import com.ecommerce.FerreViky.models.GrupoDeProductos;
import com.ecommerce.FerreViky.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface GrupoDeProductosRepository extends JpaRepository<GrupoDeProductos, Long>, JpaSpecificationExecutor<GrupoDeProductos> {
    Optional<GrupoDeProductos> findById(long id);
}
