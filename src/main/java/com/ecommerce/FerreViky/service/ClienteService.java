package com.ecommerce.FerreViky.service;

import com.ecommerce.FerreViky.dto.cliente.ClienteDTO.LoginClienteDto;
import com.ecommerce.FerreViky.dto.cliente.ClienteDTO.CreacionDeClienteRespuestaDto;
import com.ecommerce.FerreViky.exceptions.carrito.CarritoExceptions;
import com.ecommerce.FerreViky.exceptions.cliente.ClienteExceptions;
import com.ecommerce.FerreViky.mapper.cliente.ClienteMappers;
import com.ecommerce.FerreViky.models.Carrito;
import com.ecommerce.FerreViky.models.Cliente;
import com.ecommerce.FerreViky.repository.CarritoRepository;
import com.ecommerce.FerreViky.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMappers clienteMappers;
    private final CarritoRepository carritoRepository;

    /**
     * Verifica si las credenciales del cliente son válidas.
     *
     * <p>La comparación de password es en texto plano por ahora.
     * Cuando se integre Spring Security, esto se reemplazará con
     * {@code passwordEncoder.matches(dto.password(), cliente.getPassword())}.
     *
     * @param dto DTO con email y password del cliente
     * @return {@code true} si el password coincide, {@code false} si no
     * @throws ClienteExceptions.ClienteNoEncontradoException si no existe cliente con ese email
     */
    public boolean login(LoginClienteDto dto) {
        Cliente cliente = clienteRepository.findByEmail(dto.email())
                .orElseThrow(() -> new ClienteExceptions.ClienteNoEncontradoException(dto.email()));

        return cliente.getPassword().equals(dto.password());
    }

    /**
     * Registra un nuevo cliente y le crea un carrito de compras vacío de forma atómica.
     *
     * <p>El carrito se crea dentro de la misma transacción para garantizar consistencia:
     * si falla el guardado del carrito, el cliente tampoco se persiste (rollback automático).
     * El error del carrito se captura explícitamente para lanzar una excepción de dominio
     * en lugar de dejar que suba una excepción genérica de JPA.
     *
     * @param dto DTO con email y password del nuevo cliente
     * @return DTO de respuesta con el id y email del cliente creado
     * @throws ClienteExceptions.EmailYaExisteException       si el email ya está registrado
     * @throws CarritoExceptions.ErrorAlCrearCarritoException  si falla la creación del carrito
     */
    @Transactional
    public CreacionDeClienteRespuestaDto guardarCliente(LoginClienteDto dto) {
        if (clienteRepository.existsByEmail(dto.email())) {
            throw new ClienteExceptions.EmailYaExisteException(dto.email());
        }

        Cliente cliente = clienteMappers.DtoLoginACliente(dto);
        cliente = clienteRepository.save(cliente);

        Carrito carrito = new Carrito();
        carrito.setCliente(cliente);
        carrito.setFechaCreacion(LocalDateTime.now());
        try {
            carritoRepository.save(carrito);
        } catch (Exception e) {
            throw new CarritoExceptions.ErrorAlCrearCarritoException(cliente.getId(), e);
        }

        return clienteMappers.ClienteACreacion(cliente);
    }
}