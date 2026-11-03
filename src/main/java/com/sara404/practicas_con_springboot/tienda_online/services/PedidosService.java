package com.sara404.practicas_con_springboot.tienda_online.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.sara404.practicas_con_springboot.tienda_online.dto.request.PedidoRequestDTO;
import com.sara404.practicas_con_springboot.tienda_online.dto.response.ClienteResponseDTO;
import com.sara404.practicas_con_springboot.tienda_online.dto.response.PedidoResponseDTO;
import com.sara404.practicas_con_springboot.tienda_online.dto.response.ProductoResponseDTO;
import com.sara404.practicas_con_springboot.tienda_online.model.Cliente;
import com.sara404.practicas_con_springboot.tienda_online.model.Pedido;
import com.sara404.practicas_con_springboot.tienda_online.model.Producto;
import com.sara404.practicas_con_springboot.tienda_online.repository.PedidosRepository;

import jakarta.transaction.Transactional;

@Service 
public class PedidosService {

    private final PedidosRepository pedidosRepository;
    private final ClienteService clienteService;
    private final ProductoService productoService;

    
    public PedidosService(PedidosRepository pedidosRepository, ClienteService clienteService,
            ProductoService productoService) {
        this.pedidosRepository = pedidosRepository;
        this.clienteService = clienteService;
        this.productoService = productoService;
    }

    @Transactional
    public PedidoResponseDTO createPedido(PedidoRequestDTO pedidoRequest) throws Exception{

        Cliente cliente = clienteService.getCliente(pedidoRequest.idCliente());
        Producto producto = productoService.getProducto(pedidoRequest.idProducto());

        double precioPedido = pedidoRequest.precioPedido();
        int cantidad = pedidoRequest.cantidad();
        LocalDate fecha = pedidoRequest.fecha();
        
        //descontamos saldo y stock
        clienteService.descontarSaldo(precioPedido, cliente);
        productoService.reducirStock(cantidad, producto);

        //guardar pedido
        Pedido pedido = pedidosRepository.save(new Pedido(cliente, producto, cantidad, fecha, precioPedido));

        ClienteResponseDTO clienteResponse = new ClienteResponseDTO(cliente.getIdCliente(), cliente.getNombreCliente(), cliente.getEmail(), cliente.getSaldo());
        ProductoResponseDTO productoResponse = new ProductoResponseDTO(producto.getIdProducto(), producto.getNombreProducto(), producto.getPrecio(), producto.getStock());
        
        //devolver pedidoResponseDTO
        return new PedidoResponseDTO(pedido.getIdPedido(), cantidad, fecha, precioPedido, clienteResponse, productoResponse);
    }
}
