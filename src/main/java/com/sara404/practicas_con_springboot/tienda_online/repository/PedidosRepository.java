package com.sara404.practicas_con_springboot.tienda_online.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sara404.practicas_con_springboot.tienda_online.model.Pedido;

public interface PedidosRepository extends JpaRepository<Pedido, Long>{

    @Query("select p from Pedido p where p.cliente.idCliente = ?1")
    List<Pedido> findAllPedidosByClientId(Long idCliente);
}
