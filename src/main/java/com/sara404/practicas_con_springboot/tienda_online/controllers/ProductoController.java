package com.sara404.practicas_con_springboot.tienda_online.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sara404.practicas_con_springboot.tienda_online.dto.request.ProductoRequestDTO;
import com.sara404.practicas_con_springboot.tienda_online.dto.response.ProductoResponseDTO;
import com.sara404.practicas_con_springboot.tienda_online.exceptions.EntidadNoExisteException;
import com.sara404.practicas_con_springboot.tienda_online.services.ProductoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> createProducto(@Valid @RequestBody ProductoRequestDTO productoRequest) {
        ProductoResponseDTO productoResponse = service.createProducto(productoRequest);
        //Crea la url de la respuesta
        URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest() //desde la url actual
                            .path("/{id}") //añade id a la url
                            .buildAndExpand(productoResponse.idProducto()) //sustituye ese id por el del producto
                            .toUri(); 
        return ResponseEntity.created(location).body(productoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> getProductoById(@PathVariable Long id) throws EntidadNoExisteException {
        ProductoResponseDTO productoResponse = service.getProductoResponseById(id);
        return ResponseEntity.ok(productoResponse);
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> getAllProductos() {
        List<ProductoResponseDTO> productos = service.getAllProductos();
        return ResponseEntity.ok(productos);
    }
    
    //TODO métodos de modificación de un producto por su id (PUT todos los campos y/o PATCH con campos específicos)

}
