package com.ecommerce.FerreViky.controller;

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

    @PostMapping()
    public ResponseEntity<String> login(@RequestBody LoginClienteDto dto){
        //Por que un objeto DTO y no un objeto cliente..?
        //Por que no tienen los mismos datos
        boolean ok = clienteService.login(dto);
        if (!ok) return ResponseEntity.status(401).body("Credenciales incorrectas");
        return ResponseEntity.ok("Login exitoso");
    }
}
