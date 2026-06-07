package com.ecommerce.FerreViky.repository;

import com.ecommerce.FerreViky.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    boolean existsByEmail(String correo);

    Optional<Cliente> findByEmail(String correo);
}
