package com.ecommerce.FerreViky.mapper;

import com.ecommerce.FerreViky.dto.LoginClienteDto;
import com.ecommerce.FerreViky.models.Cliente;

public class ClienteMappers{

    //Convierte un objeto dto a un objeto Cliente (Para seguridad y no exponer datos incesarios) Preguntale a telcel si no xd
    public Cliente DtoLoginACliente(LoginClienteDto loginClienteDto){
        Cliente cliente = new Cliente();
        cliente.setEmail(loginClienteDto.email());
        cliente.setPassword(loginClienteDto.password());
        return cliente;
    }
}
