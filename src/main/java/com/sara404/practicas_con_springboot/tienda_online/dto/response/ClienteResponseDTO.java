package com.sara404.practicas_con_springboot.tienda_online.dto.response;

import java.math.BigDecimal;

import com.sara404.practicas_con_springboot.tienda_online.model.Cliente;

public record ClienteResponseDTO(
    Long idCliente,
    String nombreCliente,
    String email,
    BigDecimal saldo

) {

    //Devuelve un ClienteResponseDTO a partir de la entidad
    public static ClienteResponseDTO fromEntity(Cliente cliente){
        if(cliente == null) return null;
        return new ClienteResponseDTO(
            cliente.getIdCliente(),
            cliente.getNombreCliente(),
            cliente.getEmail(),
            cliente.getSaldo()
        );
    }
}
