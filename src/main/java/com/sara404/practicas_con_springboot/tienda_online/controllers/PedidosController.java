package com.sara404.practicas_con_springboot.tienda_online.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sara404.practicas_con_springboot.tienda_online.dto.request.PedidoRequestDTO;
import com.sara404.practicas_con_springboot.tienda_online.dto.response.PedidoResponseDTO;
import com.sara404.practicas_con_springboot.tienda_online.services.PedidosService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/pedidos")
public class PedidosController {

    private final PedidosService service;

    public PedidosController(PedidosService service){
        this.service = service;
    }

    //TODO modificar añadiendo Location
    public ResponseEntity<PedidoResponseDTO> createPedido(@Valid @RequestBody PedidoRequestDTO pedidoRequestDto) throws Exception{
        PedidoResponseDTO pedidoResponseDto = service.createPedido(pedidoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoResponseDto);
    }

}
