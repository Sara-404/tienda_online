package com.sara404.practicas_con_springboot.tienda_online.dto.response;

public record ProductoResponseDTO(
    Long idProducto,
    String nombreProducto,
    double precio,
    int stock
) {

}
