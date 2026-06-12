package com.ecommerce.FerreViky.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;

//Estas 4 etiquetas nos permiten no tener que escribir a mano los setters getters y constructores nada mas
@Getter //Ya no pongo getid get nombre get todos etc
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity //Se refiere a que esta CLASE de Java es una TABLA de la BD
public class Producto { //Cada objeto "producto" es una fila de la tabla producto :)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "clave", nullable = false, unique = true)
    private String clave;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "margen_mercado")
    private String margenMercado;

    @Column(name = "caja")
    private String caja;

    @Column(name = "master")
    private String master;

    @Column(name = "unidad")
    private String unidad;

    @Column(name = "ean")
    private String ean;

    @Column(name = "precio_mayoreo_iva", precision = 12, scale = 2)
    private BigDecimal precioMayoreoIva;

    @Column(name = "precio_distribuidor_iva", precision = 12, scale = 2)
    private BigDecimal precioDistribuidorIva;

    @Column(name = "precio_publico_iva", precision = 12, scale = 2)
    private BigDecimal precioPublicoIva;

    @Column(name = "marca")
    private String marca;

    // FK nullable: no todos los productos pertenecen a un GrupoDeProductos
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_generico_id", nullable = true)
    private GrupoDeProductos grupoDeProductos;

}
