package com.ecommerce.FerreViky.service;

import com.ecommerce.FerreViky.dto.producto.ProductoDTO.ProductoPublicoResponse;
import com.ecommerce.FerreViky.dto.producto.ProductoDTO;
import com.ecommerce.FerreViky.exceptions.cliente.ClienteExceptions;
import com.ecommerce.FerreViky.exceptions.productos.ProductosExceptions;
import com.ecommerce.FerreViky.mapper.Producto.ProductoMappers;
import com.ecommerce.FerreViky.models.Producto;
import com.ecommerce.FerreViky.repository.ProductoRepository;
import com.ecommerce.FerreViky.specification.ProductoSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public Page<ProductoPublicoResponse> obtenerTodos(Pageable pageable){
        return productoRepository.findAll(pageable)
                .map(ProductoMappers::toPublicoResponse);
    }

    public ProductoPublicoResponse obtenerProductoPorId(Long id){
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductosExceptions.ProductoNoEncontradoException(id));

        return ProductoMappers.toPublicoResponse(producto);
    }

}