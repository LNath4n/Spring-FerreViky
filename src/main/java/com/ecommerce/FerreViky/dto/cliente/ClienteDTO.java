package com.ecommerce.FerreViky.dto.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class ClienteDTO {

    @Schema(description = "Respuesta al registrar un nuevo cliente")
    public record CreacionDeClienteRespuestaDto(
            @Schema(description = "ID generado para el cliente", example = "42")
            Long id,

            @Schema(description = "Email registrado", example = "juan@gmail.com")
            String email
    ) {}

    @Schema(description = "Credenciales usadas tanto para login como para registro")
    public record LoginClienteDto(
            @Schema(description = "Email del cliente", example = "juan@gmail.com")
            @NotBlank String email,

            @Schema(description = "Contraseña en texto plano (sin hashing por ahora)", example = "secreto123")
            @NotBlank String password
    ) {}
}