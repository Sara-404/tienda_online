package com.sara404.practicas_con_springboot.tienda_online.exceptions;

public class SaldoInsuficienteException extends RuntimeException{

    public SaldoInsuficienteException(){
        super("El saldo del cliente es insuficiente.");
    }

}
