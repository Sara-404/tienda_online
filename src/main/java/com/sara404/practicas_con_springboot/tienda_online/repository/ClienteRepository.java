package com.sara404.practicas_con_springboot.tienda_online.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sara404.practicas_con_springboot.tienda_online.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    
}
