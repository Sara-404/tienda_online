package com.sara404.practicas_con_springboot.tienda_online.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ClienteRequestDTO(
    @NotBlank(message = "El nombre no puede estar vacío")
    String nombreCliente,
    
    @NotBlank(message = "El email es obligatorio")
    @Email (message = "El email debe ser una dirección email válida")
    String email,
    
    @NotNull (message = "El saldo es obligatorio")
    @PositiveOrZero (message = "El saldo no puede ser negativo")
    Double saldo
) {}
