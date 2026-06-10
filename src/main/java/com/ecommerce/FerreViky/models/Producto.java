package com.ecommerce.FerreViky.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
//Estas 4 etiquetas nos permiten no tener que escribir a mano los setters getters y constructores nada mas
@Getter //Ya no pongo getid get nombre get todos etc
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity //Se refiere a que esta CLASE de Java es una TABLA de la BD
public class Producto { //Cada objeto "producto" es una fila de la tabla producto :)
    @Id //Indica que esta sera la llave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica que la tabla es quien maneja genera el id
    private Long id; //Id del producto

    @Column(name = "nombre_producto", nullable = false, length = 100) //Esta linea es lo mismo que decir VARCHAR(100) not null
    private String nombreProducto;

    @Column(nullable = false, length = 50) // Lo mismo que decir VARCHAR(50) not null marca
    private String marca;

    @Column(length = 50) //VARCHAR(50)
    private String categoria;

    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    private Integer stock;

    @Column(precision = 10, scale = 2, nullable = false) //No permite nulos
    private BigDecimal precioNormal;

    @Column(precision = 10, scale = 2) //Permite nulos
    private BigDecimal precioClientes;

    //Si corrres la aplicacion sin tener creadas las tablas Spring automaticamente crea las tablas jsjs
    //Es en mi opinion mas sencillo que estar poniendo VARCHAR(100) etc Spring solito lo hace
}
