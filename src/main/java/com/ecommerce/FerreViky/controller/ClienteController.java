package com.ecommerce.FerreViky.controller;

import com.ecommerce.FerreViky.dto.CreacionDeClienteRespuestaDto;
import com.ecommerce.FerreViky.dto.LoginClienteDto;
import com.ecommerce.FerreViky.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@AllArgsConstructor
public class ClienteController {

    private final ClienteService clienteService; //Si pones el @AllArgsConstructor ya no te preocupes por inicializarlo en constructor

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginClienteDto dto){
        boolean ok = clienteService.login(dto);
        if (!ok) return ResponseEntity.status(401).body("Credenciales incorrectas");
        return ResponseEntity.ok("Login exitoso");
    }

    @PostMapping
    public ResponseEntity<CreacionDeClienteRespuestaDto> crearCliente(@RequestBody LoginClienteDto dto){
        return ResponseEntity.status(201).body(clienteService.guardarCliente(dto));
    }


    //En java hay 2 formas de tratar las excepciones
    //Uno mismo con try/catch o aventarlas para arriba xd
    //Aqui recibimos el error de el ClienteService
    //Pero Spring lo trata solito en el GlobalExceptionHandler
}
