package com.ecommerce.FerreViky.controller;

import com.ecommerce.FerreViky.dto.cliente.ClienteDTO.LoginClienteDto;
import com.ecommerce.FerreViky.dto.cliente.ClienteDTO.CreacionDeClienteRespuestaDto;
import com.ecommerce.FerreViky.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/clientes")
@AllArgsConstructor
@CrossOrigin("*")
@Tag(name = "Clientes", description = "Operaciones de autenticación y registro de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(
            summary = "Autenticar cliente",
            description = "Verifica las credenciales del cliente (email + password). " +
                    "No genera token por ahora; retorna 200 si son válidas o 401 si no."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login exitoso",
                    content = @Content(schema = @Schema(example = "{\"id\": 1}"))),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas",
                    content = @Content(schema = @Schema(example = "Credenciales incorrectas"))),
            @ApiResponse(responseCode = "404", description = "No existe cliente con ese email",
                    content = @Content(schema = @Schema(example = "No se encontró cliente con email x@x.com")))
    })
    @PostMapping("/login")
    public ResponseEntity<Map<String, Long>> login(@Valid @RequestBody LoginClienteDto dto) {
        Long id = clienteService.login(dto);
        return ResponseEntity.ok(Map.of("id", id));
    }

    @Operation(
            summary = "Registrar nuevo cliente",
            description = "Crea un nuevo cliente con email y password. " +
                    "Automáticamente le asigna un carrito de compras vacío."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente",
                    content = @Content(schema = @Schema(implementation = CreacionDeClienteRespuestaDto.class))),
            @ApiResponse(responseCode = "409", description = "El email ya está registrado",
                    content = @Content(schema = @Schema(example = "El usuario con email x@x.com ya existe"))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (e.g. email mal formado)")
    })
    @PostMapping
    public ResponseEntity<CreacionDeClienteRespuestaDto> crearCliente(@Valid @RequestBody LoginClienteDto dto) {
        return ResponseEntity.status(201).body(clienteService.guardarCliente(dto));
    }
}