package com.sara404.practicas_con_springboot.tienda_online.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity 
public class Pedido {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id_pedido")
    private Long idPedido;
    
    @ManyToOne
    @JoinColumn (name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn (name = "id_producto")
    private Producto producto;


    private int cantidad;
    private LocalDate fecha;
    @Column(name = "precio_pedido", precision = 10, scale = 2)
    private BigDecimal precioPedido;

    public Pedido(){}

    public Pedido(Cliente cliente, Producto producto, int cantidad, LocalDate fecha, BigDecimal precioPedido) {
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.precioPedido = precioPedido;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getPrecioPedido() {
        return precioPedido;
    }

    public void setPrecioPedido(BigDecimal precioPedido) {
        this.precioPedido = precioPedido;
    }

    @Override
    public String toString() {
        return "Pedido [idPedido=" + idPedido + ", cantidad=" + cantidad + ", fecha=" + fecha + ", precioPedido=" + precioPedido + "]";
    }

    
}
