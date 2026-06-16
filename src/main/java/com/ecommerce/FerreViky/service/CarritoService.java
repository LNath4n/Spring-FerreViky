package com.ecommerce.FerreViky.service;

import com.ecommerce.FerreViky.dto.carrito.CarritoDTO.AgregarCarrito;
import com.ecommerce.FerreViky.exceptions.carrito.CarritoExceptions;
import com.ecommerce.FerreViky.exceptions.cliente.ClienteExceptions;
import com.ecommerce.FerreViky.exceptions.productos.ProductosExceptions;
import com.ecommerce.FerreViky.models.Carrito;
import com.ecommerce.FerreViky.models.CarritoProducto;
import com.ecommerce.FerreViky.models.Cliente;
import com.ecommerce.FerreViky.models.Producto;
import com.ecommerce.FerreViky.repository.CarritoRepository;
import com.ecommerce.FerreViky.repository.ClienteRepository;
import com.ecommerce.FerreViky.repository.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
@Service
@AllArgsConstructor
public class CarritoService {

    private final ProductoRepository productoRepository;
    private final CarritoRepository carritoRepository;
    private final ClienteRepository clienteRepository;

    /**
     * Valida que el producto exista y que haya stock suficiente para la cantidad solicitada.
     *
     * @param idProducto ID del producto a validar
     * @param cantidad   cantidad solicitada
     * @return producto encontrado y validado
     * @throws ProductosExceptions.ProductoNoEncontradoException si el producto no existe
     * @throws CarritoExceptions.CantidadNoValida               si la cantidad es menor o igual a 0
     * @throws CarritoExceptions.CantidadExcedidaException      si la cantidad supera el stock disponible
     */
    private Producto validarProductoYStock(Long idProducto, int cantidad) {
        Producto p = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ProductosExceptions.ProductoNoEncontradoException(idProducto));

        if (cantidad <= 0)
            throw new CarritoExceptions.CantidadNoValida(cantidad);

        if (cantidad > p.getStock())
            throw new CarritoExceptions.CantidadExcedidaException(p.getStock(), cantidad, p.getDescripcion());

        return p;
    }

    /**
     * Obtiene el carrito activo del cliente, o crea uno nuevo si no tiene.
     *
     * @param c cliente dueño del carrito
     * @return carrito existente o recién creado
     */
    private Carrito obtenerOCrearCarrito(Cliente c) {
        return carritoRepository.findByClienteConProductos(c)
                .orElseGet(() -> {
                    Carrito nuevo = new Carrito();
                    nuevo.setCliente(c);
                    nuevo.setFechaCreacion(LocalDateTime.now());
                    nuevo.setProductos(new ArrayList<>());
                    return carritoRepository.save(nuevo);
                });
    }

    /**
     * Agrega un producto al carrito del cliente, o actualiza su cantidad si ya existe.
     * Si la suma de la cantidad actual más la nueva excede el stock, lanza excepción.
     *
     * @param dto datos de la operación (idCliente, idProducto, cantidad)
     * @throws ClienteExceptions.ClienteNoEncontradoException  si el cliente no existe
     * @throws ProductosExceptions.ProductoNoEncontradoException si el producto no existe
     * @throws CarritoExceptions.CantidadExcedidaException     si la cantidad total supera el stock
     */
    @Transactional
    public void agregarOActualizar(AgregarCarrito dto) {
        Producto p = validarProductoYStock(dto.idProducto(), dto.cantidad());

        Cliente c = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new ClienteExceptions.ClienteNoEncontradoException(dto.idCliente()));

        Carrito car = obtenerOCrearCarrito(c);

        Optional<CarritoProducto> itemExistente = car.getProductos().stream()
                .filter(cp -> cp.getProducto().getId().equals(p.getId()))
                .findFirst();

        if (itemExistente.isPresent()) {
            CarritoProducto item = itemExistente.get();
            int nuevaCantidad = item.getCantidad() + dto.cantidad();

            if (nuevaCantidad > p.getStock())
                throw new CarritoExceptions.CantidadExcedidaException(p.getStock(), nuevaCantidad, p.getDescripcion());

            item.setCantidad(nuevaCantidad);
        } else {
            CarritoProducto nuevoItem = new CarritoProducto();
            nuevoItem.setCarrito(car);
            nuevoItem.setProducto(p);
            nuevoItem.setCantidad(dto.cantidad());
            car.getProductos().add(nuevoItem);
        }

        carritoRepository.save(car);
    }

    /**
     * Obtiene el carrito asociado a un cliente por su ID.
     *
     * @param id ID del cliente
     * @return carrito encontrado
     * @throws CarritoExceptions.CarritoNoEncontrado si no existe carrito para ese cliente
     */
    public Carrito obtenerCarritoPorId(Long id) {
        return carritoRepository.findByClienteId(id)
                .orElseThrow(() -> new CarritoExceptions.CarritoNoEncontrado(id));
    }
}