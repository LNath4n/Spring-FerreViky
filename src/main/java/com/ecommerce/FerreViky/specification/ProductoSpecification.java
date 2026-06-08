package com.ecommerce.FerreViky.specification;

import com.ecommerce.FerreViky.models.Producto;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductoSpecification {
    //Este archivo sirve para hacer queries dinamicas, evita estar haciendo muchas combinaciones
    public static Specification<Producto> tieneNombre(String nombre){
        //Root tabla
        //Query por si queremos ordenar o usar distinct
        //CriteriaBuilder el como vamos a criticar esta informacion
        return (root, query, cb) ->
                nombre == null ? null : cb.like(
                        cb.lower(root.<String>get("nombreProducto")),
                        "%" + nombre.toLowerCase() + "%"
                );
    }//Utiliza funciones lambda que son algo confusas pero ntp
    //Aqui lo que hacemos es decirle que el Criterio para devolver informacion es un LIKE
    //Osea vamos a hacer un LIKE de sql
    //Este metodo (cb.like) pide 2 cosas el parametro de la bd y la String
    //El parametro se mete con root.get("parametro")
    //El string como string xd

    public static Specification<Producto> perteneceCategoria(String categoria){

        return (root,query,cb) ->
                categoria == null ? null : cb.equal(
                        root.<String>get("categoria"),
                        categoria.toLowerCase()
                );

    } //Si no encuentra nada regresa null , sera importante despues


    public static Specification<Producto> perteneceMarca(String marca){

        return (root,query,cb) ->
                marca == null ? null : cb.equal(
                        root.<String>get("marca"),
                        marca.toLowerCase()
                );

    }

    public static Specification<Producto> cuestaMenosDe(BigDecimal cantidad){
        return (root, query, criteriaBuilder) ->
                cantidad == null ? null : criteriaBuilder.lessThan(root.<BigDecimal>get("precioNormal"),cantidad);
    }

    public static Specification<Producto> cuestaMasDe(BigDecimal cantidad){
        return (root, query, criteriaBuilder) ->
                cantidad == null ? null : criteriaBuilder.greaterThan(root.<BigDecimal>get("precioNormal"),cantidad);
    }
}