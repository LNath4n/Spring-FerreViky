package com.ecommerce.FerreViky.repository;

import com.ecommerce.FerreViky.models.Producto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long>, JpaSpecificationExecutor<Producto> {
    // JpaSpecificationExecutor habilita findAll(Specification<T>) para el filtrado dinámico.
    // Sin esta interfaz, las JPA Specifications no funcionan aunque las definas.

    List<Producto> findByMarca(String marca);

    List<Producto> findByCategoria(String categoria);

    Optional<Producto> findById(Long id);

    boolean existsById(Long id);

    /**
     * Retorna las marcas únicas presentes en el catálogo.
     *
     * <p>Se usa JPQL con DISTINCT en vez de cargar todos los productos y filtrar en memoria,
     * lo que evita traer columnas innecesarias cuando el catálogo crece.
     */
    @Query("SELECT DISTINCT p.marca FROM Producto p")
    List<String> obtenerMarcasDistintas();

    /**
     * Retorna las categorías únicas presentes en el catálogo.
     *
     * @see #obtenerMarcasDistintas() misma razón para usar JPQL con DISTINCT
     */
    @Query("SELECT DISTINCT p.categoria FROM Producto p")
    List<String> obtenerCategoriasDistintas();
}