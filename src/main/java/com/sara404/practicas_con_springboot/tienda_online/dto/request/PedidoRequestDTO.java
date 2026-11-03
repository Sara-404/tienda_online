package com.sara404.practicas_con_springboot.tienda_online.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

public record PedidoRequestDTO(
    //cliente, producto, cantidad, fecha, precio total
    
    @NotNull (message = "El pedido debe estar asociado a un id de cliente")
    @Positive (message = "El id de cliente debe ser mayor de 0")
    Long idCliente,
    
    @NotNull (message = "El pedido debe estar asociado a un id de producto")
    @Positive (message = "El id de producto debe ser mayor de 0")
    Long idProducto,

    @NotNull (message = "La cantidad es obligatoria")
    @Positive (message = "La cantidad debe ser mayor a 0")
    int cantidad,

    @NotNull (message = "La fecha es obligatoria")
    @PastOrPresent (message = "La fecha debe ser actual o anterior al día de hoy")
    LocalDate fecha,

    @NotNull (message = "El precio del pedido es obligatorio")
    @Positive (message = "El precio del pedido debe ser mayor a 0")
    double precioPedido


) {

}
