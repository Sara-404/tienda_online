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

import org.springframework.transaction.annotation.Transactional;

@Service 
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository){
        this.repository = repository;
    }

    @Transactional 
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
    
    @Transactional 
    public void reducirStock(int cantidad, Producto producto) throws StockInsuficienteException{
        int stock = producto.getStock();
        //comprobamos si el stock es suficiente
        if(stock < cantidad) throw new StockInsuficienteException();
        producto.setStock(stock - cantidad);
    }

    //Obtiene un Producto (entidad) por su id
    @Transactional(readOnly = true)
    public Producto getProducto(Long idProducto) throws EntidadNoExisteException{
        return repository.findById(idProducto).orElseThrow(() -> new EntidadNoExisteException("El producto no existe en la base de datos"));
    }

    
    //Obtiene un ProductoResponseDTO por su id
    @Transactional(readOnly = true)
    public ProductoResponseDTO getProductoResponseById(Long id) throws EntidadNoExisteException{
        Producto producto = getProducto(id);
        return ProductoResponseDTO.fromEntity(producto);
    }
    
    //obtiene una lista de todos los productos en formato productoResponse
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> getAllProductos(){
        return repository.findAll().stream().map(producto -> ProductoResponseDTO.fromEntity(producto)).toList();
    }

    //Actualiza el nombre de un producto
    @Transactional 
    public ProductoResponseDTO updateNombreProducto(Long id, String nombre) throws EntidadNoExisteException{
        Producto producto = getProducto(id);
        producto.setNombreProducto(nombre);
        return ProductoResponseDTO.fromEntity(producto);
    }

    //Actualiza precio producto
    @Transactional
    public ProductoResponseDTO updatePrecioProducto(Long id, BigDecimal precio) throws EntidadNoExisteException{
        Producto producto = getProducto(id);
        producto.setPrecio(precio);
        return ProductoResponseDTO.fromEntity(producto);
    }

    //Aumentar stock
    @Transactional 
    public ProductoResponseDTO addStock(Long id, int cantidad) throws EntidadNoExisteException{
        Producto producto = getProducto(id);
        producto.setStock(producto.getStock() + cantidad);
        return ProductoResponseDTO.fromEntity(producto);
    }

    //Borrar producto
    @Transactional 
    public void borrarProducto(Long id) throws EntidadNoExisteException{
        Producto producto = getProducto(id);
        repository.delete(producto);
    }

}
