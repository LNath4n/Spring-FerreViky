package com.ecommerce.FerreViky.service;

import com.ecommerce.FerreViky.dto.LoginClienteDto;
import com.ecommerce.FerreViky.models.Cliente;
import com.ecommerce.FerreViky.repository.ClienteRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    public boolean login(LoginClienteDto dto) {
        Cliente cliente = clienteRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return cliente.getPassword().equals(dto.password());
    }
}
