package com.sara404.practicas_con_springboot.tienda_online.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.sara404.practicas_con_springboot.tienda_online.dto.request.ClienteRequestDTO;
import com.sara404.practicas_con_springboot.tienda_online.dto.response.ClienteResponseDTO;
import com.sara404.practicas_con_springboot.tienda_online.exceptions.EntidadNoExisteException;
import com.sara404.practicas_con_springboot.tienda_online.exceptions.SaldoInsuficienteException;
import com.sara404.practicas_con_springboot.tienda_online.repository.ClienteRepository;
import com.sara404.practicas_con_springboot.tienda_online.model.Cliente;

@Service 
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository){
        this.repository = repository;
    }

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

    
    public void descontarSaldo(BigDecimal precio, Cliente cliente) throws SaldoInsuficienteException{
        BigDecimal saldo = cliente.getSaldo();

        if(saldo.compareTo(precio) < 0){
            throw new SaldoInsuficienteException();
        }

        cliente.setSaldo(saldo.subtract(precio));
    }

    //devuelve un objeto Cliente (original, el de la entidad) a partir de su id
    public Cliente getCliente(Long idCliente) throws EntidadNoExisteException{
        return repository.findById(idCliente).orElseThrow(() -> new EntidadNoExisteException("El cliente no existe en la base de datos"));
    }

    
    //devuelve un objeto ClienteResponseDTO a partir de su id
    public ClienteResponseDTO getClienteResponseById(Long id) throws EntidadNoExisteException{
        Cliente cliente = getCliente(id);
        return ClienteResponseDTO.fromEntity(cliente);
    }

}
