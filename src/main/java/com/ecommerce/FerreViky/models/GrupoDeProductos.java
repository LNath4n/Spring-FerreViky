package com.ecommerce.FerreViky.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GrupoDeProductos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "prefijo_clave")
    private String prefijoClave;

    @Column(name = "palabras_comunes")
    private String palabrasComunes;

    @OneToMany(mappedBy = "grupoDeProductos", cascade = CascadeType.ALL)
    private List<Producto> estilos;

}