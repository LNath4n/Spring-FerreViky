package com.ecommerce.FerreViky.service;

import com.ecommerce.FerreViky.dto.LoginClienteDto;
import com.ecommerce.FerreViky.dto.CreacionDeClienteRespuestaDto;
import com.ecommerce.FerreViky.excepctions.ClienteExceptions;
import com.ecommerce.FerreViky.mapper.ClienteMappers;
import com.ecommerce.FerreViky.models.Cliente;
import com.ecommerce.FerreViky.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMappers clienteMappers;

    public boolean login(LoginClienteDto dto) {
        Cliente cliente = clienteRepository.findByEmail(dto.email())
                .orElseThrow(() -> new ClienteExceptions.ClienteNoEncontradoException(dto.email()));

        return cliente.getPassword().equals(dto.password());
    }

    public CreacionDeClienteRespuestaDto guardarCliente(LoginClienteDto dto){
        if(clienteRepository.existsByEmail(dto.email())){
            throw new ClienteExceptions.EmailYaExisteException(dto.email());
        }
        Cliente cliente = clienteMappers.DtoLoginACliente(dto);
        cliente = clienteRepository.save(cliente);

        return clienteMappers.ClienteACreacion(cliente);
    }

    //En java hay 2 formas de tratar las excepciones
    //Uno mismo con try/catch o aventarlas para arriba xd
    //Aqui al detectar error lo mandamos para arriba con throw :)
}
