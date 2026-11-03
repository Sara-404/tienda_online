package com.sara404.practicas_con_springboot.tienda_online.dto.response;

import java.time.LocalDate;

public record PedidoResponseDTO(
    Long idPedido,
    int cantidad,
    LocalDate fecha,
    double precioPedido,
    ClienteResponseDTO cliente,
    ProductoResponseDTO producto
) {

}
