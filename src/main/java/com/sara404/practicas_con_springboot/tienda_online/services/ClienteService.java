package com.sara404.practicas_con_springboot.tienda_online.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.sara404.practicas_con_springboot.tienda_online.dto.request.ClienteRequestDTO;
import com.sara404.practicas_con_springboot.tienda_online.dto.response.ClienteResponseDTO;
import com.sara404.practicas_con_springboot.tienda_online.exceptions.EntidadNoExisteException;
import com.sara404.practicas_con_springboot.tienda_online.exceptions.SaldoInsuficienteException;
import com.sara404.practicas_con_springboot.tienda_online.repository.ClienteRepository;

import org.springframework.transaction.annotation.Transactional;

import com.sara404.practicas_con_springboot.tienda_online.model.Cliente;

@Service 
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository){
        this.repository = repository;
    }

    //crea un cliente
    @Transactional
    public ClienteResponseDTO createCliente(ClienteRequestDTO clienteRequest){
        //obtenemos atributos
        String nombreCliente = clienteRequest.nombreCliente();
        String emailCliente = clienteRequest.email();
        BigDecimal saldoCliente = clienteRequest.saldo();

        //guardamos el cliente
        Cliente cliente = repository.save(new Cliente(nombreCliente, emailCliente, saldoCliente));
        
        //devolvemos el clienteResponse
        return ClienteResponseDTO.fromEntity(cliente);
    }

    //descuenta el saldo de un cliente
    @Transactional
    public void descontarSaldo(BigDecimal precio, Cliente cliente) throws SaldoInsuficienteException{
        BigDecimal saldo = cliente.getSaldo();

        if(saldo.compareTo(precio) < 0){
            throw new SaldoInsuficienteException();
        }

        cliente.setSaldo(saldo.subtract(precio));
    }

    //devuelve un objeto Cliente (original, el de la entidad) a partir de su id
    @Transactional(readOnly = true)
    public Cliente getCliente(Long idCliente) throws EntidadNoExisteException{
        return repository.findById(idCliente).orElseThrow(() -> new EntidadNoExisteException("El cliente no existe en la base de datos"));
    }
    
    //devuelve un objeto ClienteResponseDTO a partir de su id
    @Transactional(readOnly = true)
    public ClienteResponseDTO getClienteResponseById(Long id) throws EntidadNoExisteException{
        Cliente cliente = getCliente(id);
        return ClienteResponseDTO.fromEntity(cliente);
    }

    //actualiza nombre del cliente por su id
    @Transactional
    public ClienteResponseDTO updateNombreCliente(Long id, String nombre) throws EntidadNoExisteException{
        Cliente cliente = getCliente(id);
        cliente.setNombreCliente(nombre);
        return ClienteResponseDTO.fromEntity(cliente);
    }

    //actualiza el email de un cliente por su id
    @Transactional 
    public ClienteResponseDTO updateEmailCliente(Long id, String email) throws EntidadNoExisteException{
        Cliente cliente = getCliente(id);
        cliente.setEmail(email);
        return ClienteResponseDTO.fromEntity(cliente);
    }

    //incrementa el saldo de un cliente por su id
    @Transactional
    public ClienteResponseDTO subirSaldo(Long id, BigDecimal cantidad) throws EntidadNoExisteException{
        Cliente cliente = getCliente(id);
        cliente.setSaldo(cliente.getSaldo().add(cantidad));
        return ClienteResponseDTO.fromEntity(cliente);
    }

    //borra un cliente según su id
    @Transactional 
    public void borrarCliente(Long id) throws EntidadNoExisteException{
        //comprueba primero si existe
        Cliente cliente = getCliente(id);
        repository.delete(cliente);
    }

}
