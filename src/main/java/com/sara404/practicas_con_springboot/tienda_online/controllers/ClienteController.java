package com.sara404.practicas_con_springboot.tienda_online.controllers;

import java.math.BigDecimal;
import java.net.URI;

import org.springframework.http.ResponseEntity;

import com.sara404.practicas_con_springboot.tienda_online.dto.request.ClienteRequestDTO;
import com.sara404.practicas_con_springboot.tienda_online.dto.response.ClienteResponseDTO;
import com.sara404.practicas_con_springboot.tienda_online.exceptions.EntidadNoExisteException;
import com.sara404.practicas_con_springboot.tienda_online.services.ClienteService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    //actualiza el nombre de un cliente por su id
    @PatchMapping("/{id}/nombre")
    public ResponseEntity<ClienteResponseDTO> updateNombreCliente(
        @PathVariable Long id, 
        @RequestParam @NotBlank(message = "El nombre no puede estar vacío") String nombre
    ) throws EntidadNoExisteException{
        ClienteResponseDTO clienteResponseDTO = service.updateNombreCliente(id, nombre);
        return ResponseEntity.ok(clienteResponseDTO);
    }

    //actualiza el email de un cliente por su id
    @PatchMapping("/{id}/email")
    public ResponseEntity<ClienteResponseDTO> updateEmailCliente(
        @PathVariable Long id,
        @RequestParam @Email(message = "El email debe ser una dirección de email válida") String email
    ) throws EntidadNoExisteException{
        ClienteResponseDTO clienteResponseDTO = service.updateEmailCliente(id, email);
        return ResponseEntity.ok(clienteResponseDTO);
    }

    //incrementa el saldo de un cliente
    @PatchMapping("/{id}/subir-saldo")
    public ResponseEntity<ClienteResponseDTO> subirSaldo(
        @PathVariable Long id,
        @RequestParam 
        @Positive(message = "La cantidad a incrementar debe ser mayor a 0") 
        @Digits(integer = 8, fraction = 2, message = "La cantidad admite hasta 8 dígitos enteros y 2 decimales")
        BigDecimal cantidad
    ) throws EntidadNoExisteException {
        ClienteResponseDTO clienteResponseDTO = service.subirSaldo(id, cantidad);
        return ResponseEntity.ok(clienteResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarCliente(@PathVariable Long id){
        service.borrarCliente(id);
        return ResponseEntity.noContent().build();
    }

}
