package com.sara404.practicas_con_springboot.tienda_online.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sara404.practicas_con_springboot.tienda_online.dto.request.ClienteRequestDTO;
import com.sara404.practicas_con_springboot.tienda_online.dto.response.ClienteResponseDTO;
import com.sara404.practicas_con_springboot.tienda_online.services.ClienteService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("api/clientes")
public class ClienteController {
    
    private final ClienteService service;

    public ClienteController(ClienteService service){
        this.service = service;
    }

    /*
    //método para comprobar que funciona únicamente
    @GetMapping("/hello")
    public String hello() {
        return "Hello, you are in!";
    }
    */
    

    //TODO modificar añadiendo Location
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> createCliente(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        ClienteResponseDTO clienteResponse = service.createCliente(clienteRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponse);
    }
    

}
