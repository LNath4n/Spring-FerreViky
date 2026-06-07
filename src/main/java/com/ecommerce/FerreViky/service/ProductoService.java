package com.ecommerce.FerreViky.service;

import com.ecommerce.FerreViky.models.Producto;
import com.ecommerce.FerreViky.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    //Este archivo se encarga de llamar al repository y tratar con los datos de estos
    //No es "obligatorio" podria ir directo en el controller pero es buena practica

    //Declaramos el Repository, es buena practica ponerle private final
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
        //Esto es una inyeccion de dependencias, no es tan necesario ahorita iniciando pero checalo despues
        //Nos evita estar haciendo ProductoRepository productoRepository = new ProductoRepository; Spring lo maneja solo
    }

    public List<Producto> obtenerTodosLosProductos(){
        //Aqui vamos a usar el metodo que definimos en la interfaz (Repository)
        //Si te das cuenta no lo cree por que JpaRepository ya trae algunos predefinidos :)
        return productoRepository.findAll();
    }

    //Optional permite tratar nulls
    public Optional<Producto> obtenerProductoPorId(Long id){
        //Ahora si aqui utilizo uno de los que defini jsjs
        return productoRepository.findById(id);
    }

    //Devuleve una Lista de productos en base a su marca
    public List<Producto> obtenerProductoPorMarca(String marca){
        return productoRepository.findByMarca(marca);
    }

    public boolean existeProductoConId(Long id){
        return productoRepository.existsById(id);
    }

    //Jpa ya trae el metodo save que permite guardar solito el producto
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
        //Jpa solito maneja el insert :)
    }

    //Jpa tambien trae el metodo para borrar el producto en base al id
    public void eliminarProductoPorId(Long id) {
        productoRepository.deleteById(id);
    }

}
