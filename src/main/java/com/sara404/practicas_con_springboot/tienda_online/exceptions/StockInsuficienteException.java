package com.sara404.practicas_con_springboot.tienda_online.exceptions;

public class StockInsuficienteException extends RuntimeException{

    public StockInsuficienteException(){
        super("El stock del producto es insuficiente");
    }
}
