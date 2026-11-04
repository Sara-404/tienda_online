package com.sara404.practicas_con_springboot.tienda_online.services;

import java.math.BigDecimal;
import java.util.List;

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
        BigDecimal precioProducto = productoRequest.precio();
        int stockProducto = productoRequest.stock();

        ///obtenemos producto y lo guardamos en bbdd
        Producto producto = repository.save(new Producto(nombreProducto, precioProducto, stockProducto));

        //devolvemos el productoresponse
        return ProductoResponseDTO.fromEntity(producto);
    }
    
    public void reducirStock(int cantidad, Producto producto) throws StockInsuficienteException{
        int stock = producto.getStock();
        //comprobamos si el stock es suficiente
        if(stock < cantidad) throw new StockInsuficienteException();
        producto.setStock(stock - cantidad);
    }

    //Obtiene un Producto (entidad) por su id
    public Producto getProducto(Long idProducto) throws EntidadNoExisteException{
        return repository.findById(idProducto).orElseThrow(() -> new EntidadNoExisteException("El producto no existe en la base de datos"));
    }

    
    //Obtiene un ProductoResponseDTO por su id
    public ProductoResponseDTO getProductoResponseById(Long id) throws EntidadNoExisteException{
        Producto producto = getProducto(id);
        return ProductoResponseDTO.fromEntity(producto);
    }
    
    //obtiene una lista de todos los productos en formato productoResponse
    public List<ProductoResponseDTO> getAllProductos(){
        return repository.findAll().stream().map(producto -> ProductoResponseDTO.fromEntity(producto)).toList();
    }

}
