package com.ecommerce.FerreViky.service;

import com.ecommerce.FerreViky.dto.producto.ProductoDTO.ProductoResponse;
import com.ecommerce.FerreViky.exceptions.productos.ProductosExceptions;
import com.ecommerce.FerreViky.models.Producto;
import com.ecommerce.FerreViky.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    public void deberiaLanzarExcepcionCuandoProductoNoExiste(){
        // Arrange
        Mockito.when(productoRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductosExceptions.ProductoNoEncontradoException.class,
                () -> productoService.obtenerProductoPorId(1L)
        );
    }

    @Test
    public void deberiaRegresarVacioSiNoHayProductos(){
        Mockito.when(productoService.obtenerTodosLosProductos()).thenReturn(List.of());
        List<ProductoResponse> listita = productoService.obtenerTodosLosProductos();
        assertTrue(listita.isEmpty());
    }

    @Test
    void deberiaRetornarListaDeProductosCuandoExisten() {
        Producto p1 = new Producto();
        Producto p2 = new Producto();
        Mockito.when(productoRepository.findAll()).thenReturn(List.of(p1, p2));
        List<ProductoResponse> resultado = productoService.obtenerTodosLosProductos();
        assertEquals(2, resultado.size());
    }

    @Test
    void deberiaRetornarListaVaciaSiMarcaNoExiste() {
        Mockito.when(productoRepository.findByMarca("Nokia")).thenReturn(List.of());
        List<ProductoResponse> resultado = productoService.obtenerProductoPorMarca("Nokia");
        assertTrue(resultado.isEmpty());
    }

    @Test
    void deberiaRetornarTrueSiProductoExiste() {
        Mockito.when(productoRepository.existsById(1L)).thenReturn(true);
        assertTrue(productoService.existeProductoConId(1L));
    }

    @Test
    void deberiaRetornarFalseSiProductoNoExiste() {
        Mockito.when(productoRepository.existsById(99L)).thenReturn(false);
        assertFalse(productoService.existeProductoConId(99L));
    }

    @Test
    void deberiaGuardarYRetornarProductoResponse() {
        Producto productoEntrada = new Producto();
        productoEntrada.setNombreProducto("Martillo");
        Producto productoSaved = new Producto();
        productoSaved.setId(1L);
        productoSaved.setNombreProducto("Martillo");

        Mockito.when(productoRepository.save(productoEntrada)).thenReturn(productoSaved);

        ProductoResponse resultado = productoService.guardarProducto(productoEntrada);
        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
    }

}