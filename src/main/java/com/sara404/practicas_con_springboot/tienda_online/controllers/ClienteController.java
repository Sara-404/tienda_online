package com.sara404.practicas_con_springboot.tienda_online.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;

import com.sara404.practicas_con_springboot.tienda_online.dto.request.ClienteRequestDTO;
import com.sara404.practicas_con_springboot.tienda_online.dto.response.ClienteResponseDTO;
import com.sara404.practicas_con_springboot.tienda_online.exceptions.EntidadNoExisteException;
import com.sara404.practicas_con_springboot.tienda_online.services.ClienteService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("api/clientes")
public class ClienteController {
    
    private final ClienteService service;

    public ClienteController(ClienteService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> createCliente(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        ClienteResponseDTO clienteResponse = service.createCliente(clienteRequestDTO);
        //Crea la url del nuevo recurso
        URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest() //desde la url actual
                            .path("/{id}") //le añade id a la url
                            .buildAndExpand(clienteResponse.idCliente()) //sustituye ese id en la url por el del cliente
                            .toUri();
        return ResponseEntity.created(location).body(clienteResponse);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> getClienteById(@PathVariable Long id) throws EntidadNoExisteException {
        ClienteResponseDTO clienteResponseDTO = service.getClienteResponseById(id);
        return ResponseEntity.ok(clienteResponseDTO);
    }

    //TODO métodos de modificación de un cliente por su id (PUT todos los campos y/o PATCH con campos)
    
}
