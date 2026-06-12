package com.ecommerce.FerreViky.repository;

import com.ecommerce.FerreViky.dto.producto.ProductoDTO.ProductoPublicoResponse;
import com.ecommerce.FerreViky.models.Producto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long>, JpaSpecificationExecutor<Producto> {

    Optional<Producto> findById(long id);
}