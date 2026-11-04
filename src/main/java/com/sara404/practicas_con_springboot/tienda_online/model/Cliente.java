package com.sara404.practicas_con_springboot.tienda_online.model;

import java.math.BigDecimal;
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
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;
    @Column(name = "nombre_cliente")
    private String nombreCliente;
    private String email;
    @Column(name = "saldo", precision = 10, scale = 2)
    private BigDecimal saldo;


    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pedido> pedidos = new ArrayList<>();
    
    protected Cliente(){}
    
    public Cliente(String nombre, String email, BigDecimal saldo) {
        this.nombreCliente = nombre;
        this.email = email;
        this.saldo = saldo;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombre) {
        this.nombreCliente = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    //HELPER PARA SINCRONIZACIÓN
    public void addPedido(Pedido pedido){
        this.pedidos.add(pedido); //añade un pedido a la lista, sincroniza cliente
        pedido.setCliente(this); //sincroniza pedido
    }

    //HELPER PARA SINCRONIZACIÓN
    public void removePedido(Pedido pedido){
        this.pedidos.remove(pedido);
        pedido.setCliente(null);
    }

    @Override
    public String toString() {
        return "Cliente [nombre=" + nombreCliente + ", email=" + email + ", saldo=" + saldo + "]";
    }

}
