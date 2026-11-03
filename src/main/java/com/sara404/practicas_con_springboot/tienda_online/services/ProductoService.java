package com.sara404.practicas_con_springboot.tienda_online.services;

import org.springframework.stereotype.Service;

import com.sara404.practicas_con_springboot.tienda_online.dto.request.ProductoRequestDTO;
import com.sara404.practicas_con_springboot.tienda_online.dto.response.ProductoResponseDTO;
import com.sara404.practicas_con_springboot.tienda_online.exceptions.EntidadNoExisteException;
import com.sara404.practicas_con_springboot.tienda_online.exceptions.StockInsuficienteException;
import com.sara404.practicas_con_springboot.tienda_online.model.Producto;
import com.sara404.practicas_con_springboot.tienda_online.repository.ProductoRepository;

@Service 
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository){
        this.repository = repository;
    }

    public ProductoResponseDTO createProducto(ProductoRequestDTO productoRequest){
        //obtener atributos
        String nombreProducto = productoRequest.nombreProducto();
        Double precioProducto = productoRequest.precio();
        int stockProducto = productoRequest.stock();

        ///obtenemos producto y lo guardamos en bbdd
        Producto producto = repository.save(new Producto(nombreProducto, precioProducto, stockProducto));

        //devolvemos el productoresponse
        return new ProductoResponseDTO(producto.getIdProducto(), nombreProducto, precioProducto, stockProducto);
    }
    
    public void reducirStock(int cantidad, Producto producto) throws StockInsuficienteException{
        int stock = producto.getStock();
        //comprobamos si el stock es suficiente
        if(stock < cantidad) throw new StockInsuficienteException();
        producto.setStock(stock - cantidad);
    }

    public Producto getProducto(Long idProducto) throws EntidadNoExisteException{
        return repository.findById(idProducto).orElseThrow(() -> new EntidadNoExisteException("El producto no existe en la base de datos"));
    }

}
