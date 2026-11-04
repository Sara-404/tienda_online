package com.sara404.practicas_con_springboot.tienda_online.dto.response;

import java.math.BigDecimal;

import com.sara404.practicas_con_springboot.tienda_online.model.Producto;

public record ProductoResponseDTO(
    Long idProducto,
    String nombreProducto,
    BigDecimal precio,
    int stock
) {

    //Devuelve un ProductoResponseDTO a partir de la entidad
    public static ProductoResponseDTO fromEntity(Producto producto){
        if(producto == null){
            return null;
        }
        return new ProductoResponseDTO(
            producto.getIdProducto(),
            producto.getNombreProducto(),
            producto.getPrecio(),
            producto.getStock()
        );
    }
}
