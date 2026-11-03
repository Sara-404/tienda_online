package com.sara404.practicas_con_springboot.tienda_online.services;

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
        Double saldoCliente = clienteRequest.saldo();

        //guardamos el cliente
        Cliente cliente = repository.save(new Cliente(nombreCliente, emailCliente, saldoCliente));
        
        //devolvemos el clienteResponse
        return new ClienteResponseDTO(cliente.getIdCliente(), nombreCliente, emailCliente, saldoCliente);
    }

    
    public void descontarSaldo(Double precio, Cliente cliente) throws SaldoInsuficienteException{
        double saldo = cliente.getSaldo();

        if(saldo < precio){
            throw new SaldoInsuficienteException();
        }

        cliente.setSaldo(saldo - precio);
    }

    public Cliente getCliente(Long idCliente) throws EntidadNoExisteException{
        return repository.findById(idCliente).orElseThrow(() -> new EntidadNoExisteException("El cliente no existe en la base de datos"));
    }

}
