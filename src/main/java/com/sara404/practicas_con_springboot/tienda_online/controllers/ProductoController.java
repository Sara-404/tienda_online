package com.sara404.practicas_con_springboot.tienda_online.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sara404.practicas_con_springboot.tienda_online.dto.request.ProductoRequestDTO;
import com.sara404.practicas_con_springboot.tienda_online.dto.response.ProductoResponseDTO;
import com.sara404.practicas_con_springboot.tienda_online.services.ProductoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service){
        this.service = service;
    }

    //TODO modificar con Location
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> createProducto(@Valid @RequestBody ProductoRequestDTO productoRequest) {
        ProductoResponseDTO productoResponse = service.createProducto(productoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoResponse);
    }
    

}
