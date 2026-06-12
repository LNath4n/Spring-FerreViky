package com.ecommerce.FerreViky.repository;

import com.ecommerce.FerreViky.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Comprueba si ya existe un cliente con el email dado.
     *
     * <p>Se usa antes de guardar un nuevo cliente para lanzar una excepción
     * de dominio clara ({@link com.ecommerce.FerreViky.exceptions.cliente.ClienteExceptions.EmailYaExisteException})
     * en vez de dejar que falle la constraint {@code unique} de la BD con una excepción genérica de JPA.
     *
     * @param correo email a verificar
     * @return {@code true} si el email ya está registrado
     */
    boolean existsByEmail(String correo);

    /**
     * Busca un cliente por su email.
     *
     * <p>Retorna {@link Optional} en lugar de {@code null} para obligar al
     * llamador a manejar explícitamente el caso de cliente no encontrado.
     *
     * @param correo email del cliente
     * @return Optional con el cliente, o vacío si no existe
     */
    Optional<Cliente> findByEmail(String correo);

    // findById ya viene heredado de JpaRepository — se redeclara aquí solo
    // para que el IDE lo autocomplete con el tipo correcto sin casteo.
    Optional<Cliente> findById(Long id);
}