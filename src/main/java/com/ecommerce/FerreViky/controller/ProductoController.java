package com.ecommerce.FerreViky.controller;

import com.ecommerce.FerreViky.dto.producto.ProductoDTO.ProductoFiltroRequest;
import com.ecommerce.FerreViky.dto.producto.ProductoDTO.ProductoResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/productos")
@Tag(name = "Productos", description = "Consulta, creación y eliminación de productos del catálogo")
public class ProductoController {

    private final ProductoService productoService;

    ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(summary = "Obtener todos los productos", description = "Retorna el catálogo completo sin ningún filtro.")
    @ApiResponse(responseCode = "200", description = "Lista de productos (puede ser vacía)")
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> obtenerTodosLosProductos() {
        return ResponseEntity.ok(productoService.obtenerTodosLosProductos());
    }

    @Operation(summary = "Obtener producto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "No existe producto con ese ID",
                    content = @Content(schema = @Schema(example = "No se encontró producto con id 99")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerProductoPorId(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerProductoPorId(id));
    }

    @Operation(summary = "Filtrar productos por marca")
    @ApiResponse(responseCode = "200", description = "Lista de productos de esa marca (puede ser vacía)")
    @GetMapping("/marcas/{marca}")
    public ResponseEntity<List<ProductoResponse>> obtenerProductosPorMarcas(
            @Parameter(description = "Nombre de la marca", example = "Trupper")
            @PathVariable String marca) {
        return ResponseEntity.ok(productoService.obtenerProductoPorMarca(marca));
    }

    @Operation(summary = "Filtrar productos por categoría")
    @ApiResponse(responseCode = "200", description = "Lista de productos de esa categoría (puede ser vacía)")
    @GetMapping("/categorias/{categoria}")
    public ResponseEntity<List<ProductoResponse>> obtenerProductosPorCategorias(
            @Parameter(description = "Nombre de la categoría", example = "Herramientas")
            @PathVariable String categoria) {
        return ResponseEntity.ok(productoService.obtenerProductoPorCategoria(categoria));
    }

    @Operation(
            summary = "Búsqueda avanzada con filtros",
            description = "Filtra productos combinando nombre, marca, categoría y rango de precio. " +
                    "Todos los parámetros son opcionales; los que se omiten no se aplican al filtro."
    )
    @ApiResponse(responseCode = "200", description = "Lista de productos que cumplen los filtros")
    @GetMapping("/busqueda")
    public ResponseEntity<List<ProductoResponse>> buscar(@ModelAttribute ProductoFiltroRequest filtro) {
        return ResponseEntity.ok(productoService.filtrar(filtro));
    }

    @Operation(summary = "Obtener todas las marcas disponibles", description = "Retorna valores DISTINCT de marca en el catálogo.")
    @ApiResponse(responseCode = "200", description = "Lista de marcas únicas")
    @GetMapping("/marcas")
    public ResponseEntity<List<String>> obtenerMarcas() {
        return ResponseEntity.ok(productoService.obtenerMarcas());
    }

    @Operation(summary = "Obtener todas las categorías disponibles", description = "Retorna valores DISTINCT de categoría en el catálogo.")
    @ApiResponse(responseCode = "200", description = "Lista de categorías únicas")
    @GetMapping("/categorias")
    public ResponseEntity<List<String>> obtenerCategorias() {
        return ResponseEntity.ok(productoService.obtenerCategorias());
    }

    @Operation(
            summary = "Crear producto",
            description = "Guarda un nuevo producto en el catálogo. " +
                    "El header Location de la respuesta apunta al endpoint del producto creado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductoResponse> crearProducto(
            @Valid @RequestBody Producto producto,
            UriComponentsBuilder ucb) {
        ProductoResponse nuevo = productoService.guardarProducto(producto);
        URI location = ucb.path("/productos/{id}")
                .buildAndExpand(nuevo.id())
                .toUri();
        return ResponseEntity.created(location).body(nuevo);
    }

    @Operation(summary = "Eliminar producto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "No existe producto con ese ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarProductoPorId(
            @Parameter(description = "ID del producto a eliminar", example = "1")
            @PathVariable Long id) {
        if (!productoService.existeProductoConId(id)) {
            return ResponseEntity.notFound().build();
        }
        productoService.eliminarProductoPorId(id);
        return ResponseEntity.noContent().build();
    }
}