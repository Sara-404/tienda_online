package com.sara404.practicas_con_springboot.tienda_online.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity 
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_producto")
    private Long idProducto;
    @Column(name = "nombre_producto")
    private String nombreProducto;
    private double precio;
    private int stock;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pedido> pedidos = new ArrayList<>();

    public Producto(){}

    public Producto(String nombreProducto, double precio, int stock) {
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.stock = stock;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    //HELPER PARA SINCRONIZACIÓN
    public void addPedido(Pedido pedido){
        this.pedidos.add(pedido); //Sincroniza producto. Añade un pedido a la lista de pedidos de producto.
        pedido.setProducto(this); //Sicroniza pedido.
    }

    //HELPER PARA SINCRONIZACIÓN
    public void removePedido(Pedido pedido){
        this.pedidos.remove(pedido);
        pedido.setProducto(null);
    }

    @Override
    public String toString() {
        return "Producto [nombreProducto=" + nombreProducto + ", precio=" + precio + ", stock=" + stock + "]";
    }


}
