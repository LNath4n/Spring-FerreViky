package com.ecommerce.FerreViky.service;

import com.ecommerce.FerreViky.dto.producto.ProductoDTO.ProductoFiltroRequest;
import com.ecommerce.FerreViky.dto.producto.ProductoDTO.ProductoResponse;
import com.ecommerce.FerreViky.exceptions.productos.ProductosExceptions;
import com.ecommerce.FerreViky.mapper.Producto.ProductoMappers;
import com.ecommerce.FerreViky.models.Producto;
import com.ecommerce.FerreViky.repository.ProductoRepository;
import com.ecommerce.FerreViky.specification.ProductoSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<ProductoResponse> obtenerTodosLosProductos() {
        return productoRepository.findAll()
                .stream()
                .map(ProductoMappers::toResponse)
                .toList();
    }

    public ProductoResponse obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .map(ProductoMappers::toResponse)
                .orElseThrow(() -> new ProductosExceptions.ProductoNoEncontradoException(id));
    }

    public List<ProductoResponse> obtenerProductoPorMarca(String marca) {
        return productoRepository.findByMarca(marca)
                .stream()
                .map(ProductoMappers::toResponse)
                .toList();
    }

    public List<ProductoResponse> obtenerProductoPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria)
                .stream()
                .map(ProductoMappers::toResponse)
                .toList();
    }

    public boolean existeProductoConId(Long id) {
        return productoRepository.existsById(id);
    }

    public ProductoResponse guardarProducto(Producto producto) {
        return ProductoMappers.toResponse(productoRepository.save(producto));
    }

    public void eliminarProductoPorId(Long id) {
        productoRepository.deleteById(id);
    }

    /**
     * Filtra productos combinando múltiples criterios opcionales mediante JPA Specifications.
     *
     * <p>Cada criterio se construye como una {@link Specification} independiente y se encadena
     * con {@code .and()}. Si un campo del filtro viene {@code null}, su Specification
     * devuelve un predicado neutro y no afecta la query — lo que permite que todos los
     * parámetros sean opcionales sin condicionales en este método.
     *
     * @param filtro DTO con los campos de búsqueda (todos opcionales)
     * @return lista de productos que cumplen todos los criterios activos
     */
    public List<ProductoResponse> filtrar(ProductoFiltroRequest filtro) {
        Specification<Producto> spec = Specification
                .where(ProductoSpecification.tieneNombre(filtro.nombre()))
                .and(ProductoSpecification.perteneceCategoria(filtro.categoria()))
                .and(ProductoSpecification.perteneceMarca(filtro.marca()))
                .and(ProductoSpecification.cuestaMasDe(filtro.precioMin()))
                .and(ProductoSpecification.cuestaMenosDe(filtro.precioMax()));

        return productoRepository.findAll(spec)
                .stream()
                .map(ProductoMappers::toResponse)
                .toList();
    }

    public List<String> obtenerCategorias() {
        return productoRepository.obtenerCategoriasDistintas();
    }

    public List<String> obtenerMarcas() {
        return productoRepository.obtenerMarcasDistintas();
    }
}