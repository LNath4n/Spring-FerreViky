package com.ecommerce.FerreViky.mapper.cliente;

import com.ecommerce.FerreViky.dto.cliente.ClienteDTO.CreacionDeClienteRespuestaDto;
import com.ecommerce.FerreViky.dto.cliente.ClienteDTO.LoginClienteDto;
import com.ecommerce.FerreViky.models.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMappers {

    /**
     * Convierte el DTO de login/registro a una entidad {@link Cliente}.
     *
     * <p>Intencionalmente solo mapea email y password para no exponer
     * ni persistir campos que el cliente no deba controlar (e.g. id, roles).
     */
    public Cliente DtoLoginACliente(LoginClienteDto loginClienteDto) {
        Cliente cliente = new Cliente();
        cliente.setEmail(loginClienteDto.email());
        cliente.setPassword(loginClienteDto.password());
        return cliente;
    }

    public CreacionDeClienteRespuestaDto ClienteACreacion(Cliente cliente) {
        return new CreacionDeClienteRespuestaDto(
                cliente.getId(), cliente.getEmail()
        );
    }
}