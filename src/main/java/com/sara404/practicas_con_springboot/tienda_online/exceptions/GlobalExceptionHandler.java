package com.sara404.practicas_con_springboot.tienda_online.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> manejarErroresValidacion(
        MethodArgumentNotValidException e
    ){
        Map<String, String> errores = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    @ExceptionHandler(EntidadNoExisteException.class)
    public ResponseEntity<String> manejarEntidadNoExiste(
        EntidadNoExisteException e
    ){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<String> manejarSaldoInsuficiente(
        SaldoInsuficienteException e
    ){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<String> manejarStockInsuficiente(
        StockInsuficienteException e
    ){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
