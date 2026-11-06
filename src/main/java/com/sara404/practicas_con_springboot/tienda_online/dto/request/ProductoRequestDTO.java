package com.sara404.practicas_con_springboot.tienda_online.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductoRequestDTO(
    
    @NotBlank (message = "El nombre del producto no puede estar vacío")
    @NotNull (message = "El nombre del producto es obligatorio")
    String nombreProducto,

    @NotNull (message = "El precio del producto es obligatorio")
    @PositiveOrZero (message = "El precio de un producto no puede ser negativo")
    @Digits(integer = 8, fraction = 2, message = "El precio admite hasta 8 dígitos enteros y 2 decimales")
    BigDecimal precio,

    @NotNull (message = "El stock es obligatorio")
    @PositiveOrZero (message = "El stock no puede ser negativo")
    int stock

) {

}
