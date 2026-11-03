package com.sara404.practicas_con_springboot.tienda_online.exceptions;

public class SaldoInsuficienteException extends Exception{

    public SaldoInsuficienteException(){
        super("El saldo del cliente es insuficiente.");
    }

}
