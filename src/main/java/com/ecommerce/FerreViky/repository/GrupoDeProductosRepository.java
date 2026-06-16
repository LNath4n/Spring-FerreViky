package com.ecommerce.FerreViky.repository;

import com.ecommerce.FerreViky.models.GrupoDeProductos;
import com.ecommerce.FerreViky.models.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GrupoDeProductosRepository extends JpaRepository<GrupoDeProductos, Long>, JpaSpecificationExecutor<GrupoDeProductos> {
    Optional<GrupoDeProductos> findById(long id);
    @Query(value = "SELECT g FROM GrupoDeProductos g",
            countQuery = "SELECT COUNT(g) FROM GrupoDeProductos g")
    Page<GrupoDeProductos> findAllPaged(Pageable pageable);

    @Query("SELECT g FROM GrupoDeProductos g LEFT JOIN FETCH g.estilos WHERE g IN :grupos")
    List<GrupoDeProductos> findWithEstilos(@Param("grupos") List<GrupoDeProductos> grupos);
}
