package com.ecommerce.FerreViky.controller;

import com.ecommerce.FerreViky.dto.producto.ProductoFiltroRequest;
import com.ecommerce.FerreViky.models.Producto;
import com.ecommerce.FerreViky.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
//Indica que la clase es un controlador el cual maneja peticiones etc, Rest para apis
// @Controller para por ejemplo usaramos HTML pero al ser api pura es mejor RestController
@CrossOrigin("*")
//Indica desde donde puede recibir llamadas, lo configuraremos despues pero de momento no es importante
@RequestMapping("/productos") //Todas las llamadas a este controller inician con /productos, no es obligatorio pero se recomienda jsjs
public class ProductoController {

    private final ProductoService productoService;

    ProductoController(ProductoService productoService){
        this.productoService= productoService;
        //Inyectamos la dependencia que necesitamos
    }

    //GET obtener info
    //POST crear
    //Put remplazo total
    //Patch actualizacion parcial
    //Delete borrar

    @GetMapping() //Indica que recibe un metodo Get
    public ResponseEntity<List<Producto>> obtenerTodosLosProductos(){ //El response entity me permite saber si hubo algun error
        //200 ok
        //400 error del usuario
        //500 error del servidor
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        return ResponseEntity.ok(productos); //Devuelve un 200 de que todo bien
    }

    @GetMapping("/marcas/{marca}") //Se recomienda que el recurso debe ser un sustantivo plural
    public ResponseEntity<List<Producto>> obtenerProductosPorMarcas(@PathVariable String marca){//@PathVariable indica que la marca vendra asi /marcas/Trupper
        List<Producto> productosPorMarca = productoService.obtenerProductoPorMarca(marca);
        return ResponseEntity.ok(productosPorMarca);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id){
        return productoService.obtenerProductoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        //Este es algo mas complejo jaja, pero son funciones Lambda
        //La funcion map convierte un objeto en otro en este caso en un RespnseEntity ok (Internamente hace esto Producto -> ResponseEntity<Producto>)
        //Y si no en un error 404
        //Osea si existe da 200 ok y el producto, si no da error 404 not found
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarProductoPorId(@PathVariable Long id){

        if(!productoService.existeProductoConId(id)){
            return ResponseEntity.notFound().build();
        } //Si no existe devolvemos un error 404 not found

        productoService.eliminarProductoPorId(id); // Borramos

        return ResponseEntity.noContent().build(); //Mandamos exito 204 que es el estandar para borrar
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED) //Devuelve 201 estandar de creado
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto, UriComponentsBuilder ucb){ //RequestBody indica que nos va a llegar un objeto
        //Validated verifica que si cumple con las condiciones (por ejemplo not null)

        Producto nuevo = productoService.guardarProducto(producto);
        URI location = ucb.path("/productos/{id}")
                .buildAndExpand(nuevo.getId())
                .toUri(); //Esto solo sirve para poder saber cual es la ID del nuevo producto sin tener que ir a buscar la id
        //Es algo mas "detalle" que necesaario jeje

        return ResponseEntity.created(location).body(nuevo);
    }


    @GetMapping("/categorias/{categorias}") //Se recomienda que el recurso debe ser un sustantivo plural
    public ResponseEntity<List<Producto>> obtenerProductosPorCategorias(@PathVariable String categoria){//@PathVariable indica que la marca vendra asi /marcas/Trupper
        List<Producto> productosPorCategoria = productoService.obtenerProductoPorCategoria(categoria);
        return ResponseEntity.ok(productosPorCategoria);
    }

    @GetMapping("/busqueda")
    public ResponseEntity<List<Producto>> buscar(
            @ModelAttribute ProductoFiltroRequest filtro) {
        List<Producto> productos = productoService.filtrar(filtro);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/marcas")
    public List<String> obtenerMarcas(){
        return productoService.obtenerMarcas();
    }
    @GetMapping("/categorias")
    public List<String> obtenerCategorias(){
        return productoService.obtenerCategorias();
    }
}
