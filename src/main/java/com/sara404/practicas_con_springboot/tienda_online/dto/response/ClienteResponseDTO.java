package com.sara404.practicas_con_springboot.tienda_online.dto.response;

public record ClienteResponseDTO(
    Long idCliente,
    String nombreCliente,
    String email,
    double saldo

) {

}
