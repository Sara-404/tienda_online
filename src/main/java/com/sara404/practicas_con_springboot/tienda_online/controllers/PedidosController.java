package com.sara404.practicas_con_springboot.tienda_online.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sara404.practicas_con_springboot.tienda_online.dto.request.PedidoRequestDTO;
import com.sara404.practicas_con_springboot.tienda_online.dto.response.PedidoResponseDTO;
import com.sara404.practicas_con_springboot.tienda_online.services.PedidosService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("api/pedidos")
public class PedidosController {

    private final PedidosService service;

    public PedidosController(PedidosService service){
        this.service = service;
    }

    
    @PostMapping 
    public ResponseEntity<PedidoResponseDTO> createPedido(@Valid @RequestBody PedidoRequestDTO pedidoRequestDto) throws Exception{
        PedidoResponseDTO pedidoResponseDto = service.createPedido(pedidoRequestDto);
        //crea la url del nuevo recurso
        URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest() //desde la url actual
                            .path("/{id}") //le añade el id a la url
                            .buildAndExpand(pedidoResponseDto.idPedido()) //sustituye ese id por el de la respuesta (pedido)
                            .toUri();
        return ResponseEntity.created(location).body(pedidoResponseDto);
    }

    //Obtiene lista de pedidos según el id del cliente
    @GetMapping("/{idCliente}")
    public ResponseEntity<List<PedidoResponseDTO>> getListaPedidos(@PathVariable Long idCliente) {
        List<PedidoResponseDTO> pedidosResponse = service.getListaPedidos(idCliente);
        return ResponseEntity.ok(pedidosResponse);
    }
    

    //TODO métodos de modificación de un pedido por su id (PUT todos los campos y/o PATCH con campos específicos)

}
