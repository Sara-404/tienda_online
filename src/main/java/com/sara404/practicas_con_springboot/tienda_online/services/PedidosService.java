package com.sara404.practicas_con_springboot.tienda_online.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sara404.practicas_con_springboot.tienda_online.dto.request.PedidoRequestDTO;
import com.sara404.practicas_con_springboot.tienda_online.dto.response.PedidoResponseDTO;
import com.sara404.practicas_con_springboot.tienda_online.exceptions.EntidadNoExisteException;
import com.sara404.practicas_con_springboot.tienda_online.exceptions.SaldoInsuficienteException;
import com.sara404.practicas_con_springboot.tienda_online.exceptions.StockInsuficienteException;
import com.sara404.practicas_con_springboot.tienda_online.model.Cliente;
import com.sara404.practicas_con_springboot.tienda_online.model.Pedido;
import com.sara404.practicas_con_springboot.tienda_online.model.Producto;
import com.sara404.practicas_con_springboot.tienda_online.repository.PedidosRepository;

import org.springframework.transaction.annotation.Transactional;

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
    public PedidoResponseDTO createPedido(PedidoRequestDTO pedidoRequest) 
        throws EntidadNoExisteException, SaldoInsuficienteException, StockInsuficienteException
        {
        //Obtenemos cliente, producto, cantidad y fecha
        Cliente cliente = clienteService.getCliente(pedidoRequest.idCliente());
        Producto producto = productoService.getProducto(pedidoRequest.idProducto());
        int cantidad = pedidoRequest.cantidad();
        LocalDate fecha = pedidoRequest.fecha();
        
        //Calculamos el precio del pedido según el precio del producto y la cantidad
        BigDecimal precioPedido = producto.getPrecio().multiply(BigDecimal.valueOf(cantidad));
        
        //descontamos saldo y stock
        clienteService.descontarSaldo(precioPedido, cliente);
        productoService.reducirStock(cantidad, producto);

        //guardar pedido
        Pedido pedido = pedidosRepository.save(new Pedido(cliente, producto, cantidad, fecha, precioPedido));

        //devolver pedidoResponseDTO
        return PedidoResponseDTO.fromEntity(pedido);
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> getListaPedidos(Long id){
        return pedidosRepository.findAllPedidosByClientId(id).stream().map(pedido -> PedidoResponseDTO.fromEntity(pedido)).toList();
    }

    @Transactional(readOnly = true)
    public Pedido getPedido(Long id) throws EntidadNoExisteException{
        return pedidosRepository.findById(id).orElseThrow(() -> new EntidadNoExisteException("El pedido con el id " + id + " no existe"));
    }

    @Transactional
    public void cancelarPedido(Long id){
        Pedido pedido = getPedido(id);
        
        //devolver el dinero al cliente
        Cliente cliente = pedido.getCliente();
        BigDecimal precio = pedido.getPrecioPedido();
        cliente.setSaldo(cliente.getSaldo().add(precio));
        
        //devolver el stock a producto
        Producto producto = pedido.getProducto();
        int cantidad = pedido.getCantidad();
        producto.setStock(producto.getStock() + cantidad);

        //eliminar pedido
        pedidosRepository.delete(pedido);
    }

}
