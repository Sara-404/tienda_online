package com.sara404.practicas_con_springboot.tienda_online.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.sara404.practicas_con_springboot.tienda_online.model.Pedido;

public record PedidoResponseDTO(
    Long idPedido,
    int cantidad,
    LocalDate fecha,
    BigDecimal precioPedido,
    ClienteResponseDTO cliente,
    ProductoResponseDTO producto
) {
    //Devuelve un PedidoResponseDTO a partir de la entidad
    public static PedidoResponseDTO fromEntity(Pedido pedido){
        if(pedido == null) return null;
        return new PedidoResponseDTO(
            pedido.getIdPedido(),
            pedido.getCantidad(),
            pedido.getFecha(),
            pedido.getPrecioPedido(),
            ClienteResponseDTO.fromEntity(pedido.getCliente()),
            ProductoResponseDTO.fromEntity(pedido.getProducto())
        );
    }

}
