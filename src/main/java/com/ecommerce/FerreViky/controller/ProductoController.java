package com.ecommerce.FerreViky.controller;

import com.ecommerce.FerreViky.dto.GruposDeProductos.GruposDeProductosDTO.GrupoPublicoResponse;
import com.ecommerce.FerreViky.dto.producto.ProductoDTO;
import com.ecommerce.FerreViky.dto.producto.ProductoDTO;
import com.ecommerce.FerreViky.models.Producto;
import com.ecommerce.FerreViky.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import com.ecommerce.FerreViky.dto.producto.ProductoDTO.ProductoPublicoResponse;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/productos")
@Tag(name = "Productos", description = "Consulta, creación y eliminación de productos del catálogo")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public Page<ProductoPublicoResponse> obtenerTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productoService.obtenerTodos(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoPublicoResponse> obtenerPorId(@PathVariable Long id) {
        ProductoPublicoResponse producto = productoService.obtenerProductoPorId(id);
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/grupos")
    public Page<GrupoPublicoResponse> obtenerTodosLosGrupos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productoService.obtenerTodosLosGrupos(pageable);
    }

    @GetMapping("/grupos/{id}")
    public ResponseEntity<GrupoPublicoResponse> obtenerGrupoPorId(@PathVariable Long id) {
        GrupoPublicoResponse grupoPublicoResponse = productoService.obtenerGrupoPorId(id);
        return ResponseEntity.ok(grupoPublicoResponse);
    }
}
