package com.ecommerce.FerreViky.controller;

import com.ecommerce.FerreViky.dto.carrito.CarritoDTO.AgregarCarrito;
import com.ecommerce.FerreViky.models.Carrito;
import com.ecommerce.FerreViky.service.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
@AllArgsConstructor
@CrossOrigin("*")
@Tag(name="Carrito",description = "Operaciones de registro y consulta de carrito de productos")
public class CarritoController {

    private final CarritoService carritoService;

    @Operation(
            summary = "Agregar o actualizar un Producto al carrito de productos",
            description = "Verifica la existencia o no de un producto en el carrito de productos"+
                    " si existe actualiza la cantidad, si no la ingresa"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Se agrego correctamente el producto al carrito"),
            @ApiResponse(responseCode = "401",description = "La cantidad del producto es invalida"),
            @ApiResponse(responseCode = "401",description = "No hay stock suficiente"),
            @ApiResponse(responseCode = "404",description = "No se encontro el producto solicitado"),
            @ApiResponse(responseCode = "404",description = "No se encontro el cliente solicitado"),
    })
    @PostMapping()
    public ResponseEntity<String> agregarCarrito(@RequestBody AgregarCarrito dto) {
        carritoService.agregarOActualizar(dto);
        return ResponseEntity.ok("Producto agregado al carrito");
    }


    @Operation(summary="Obtener carrito del cliente",description = "Se obtiene el carrito de productos en base a el id")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Se obtuvo correctamente el carrito del cliente"),
            @ApiResponse(responseCode = "404",description = "No se encontro  el carrito del cliente"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Carrito> obtenerCarritoPorId(@PathVariable Long id){
        Carrito carrito = carritoService.obtenerCarritoPorId(id);
        return ResponseEntity.ok(carrito);
    }
}
