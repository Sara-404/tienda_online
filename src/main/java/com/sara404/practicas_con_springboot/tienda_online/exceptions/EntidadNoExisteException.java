package com.sara404.practicas_con_springboot.tienda_online.exceptions;

public class EntidadNoExisteException extends RuntimeException{

    public EntidadNoExisteException(String message){
        super(message);
    }

}
