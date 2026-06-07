package com.ecommerce.FerreViky.repository;
import com.ecommerce.FerreViky.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto,Long>{
    //Este archivo DEFINE las Queries que vamos a ocupar
    //Spring trae metodos que son equivalentes a Queries
    //Por ejemplo findAll() es lo mismo que SELECT * FROM ....
    //Tenemos que especificarle que tabla es <Producto> y el tipo llave primaria de este <Long>


    List<Producto> findByMarca(String marca);
    //Esta funcion es equivalente a SELECT marca FROM Producto;
    //Indica que vamos a devolver una Lista de Productos

    List<Producto> findByCategoria(String categoria);

    //Optional permite tratar nulls
    Optional<Producto> findById(Long id);
    //Indica que vamos a devolver un producto
    //Es lo mismo que SELECT * FROM Producto where Id =....

    boolean existsById(Long id);

}
