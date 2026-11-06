package com.sara404.practicas_con_springboot.tienda_online.controllers;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sara404.practicas_con_springboot.tienda_online.dto.request.ProductoRequestDTO;
import com.sara404.practicas_con_springboot.tienda_online.dto.response.ProductoResponseDTO;
import com.sara404.practicas_con_springboot.tienda_online.exceptions.EntidadNoExisteException;
import com.sara404.practicas_con_springboot.tienda_online.services.ProductoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    
    @PatchMapping("/{id}/nombre")
    public ResponseEntity<ProductoResponseDTO> updateNombreProducto(
        @PathVariable Long id,
        @NotBlank(message = "El nombre no puede estar vacío") 
        @RequestParam String nombre
    ){
        ProductoResponseDTO productoResponse = service.updateNombreProducto(id, nombre);
        return ResponseEntity.ok(productoResponse);
    }

    @PatchMapping("/{id}/precio")
    public ResponseEntity<ProductoResponseDTO> updatePrecioProducto(
        @PathVariable Long id,
        @RequestParam 
        @Positive(message = "El precio debe ser mayor a 0") 
        @Digits(integer = 8, fraction = 2, message = "El precio admite hasta 8 dígitos enteros y 2 decimales")
        BigDecimal precio
    ){
        ProductoResponseDTO productoResponse = service.updatePrecioProducto(id, precio);
        return ResponseEntity.ok(productoResponse);
    }

    @PatchMapping("/{id}/subir-stock")
    public ResponseEntity<ProductoResponseDTO> addStock(
        @PathVariable Long id,
        @RequestParam
        @Positive(message = "El stock a subir debe ser mayor a 0")
        int cantidad 
    ){
        ProductoResponseDTO productoResponse = service.addStock(id, cantidad);
        return ResponseEntity.ok(productoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarProducto(@PathVariable Long id){
        service.borrarProducto(id);
        return ResponseEntity.noContent().build();
    }

}
