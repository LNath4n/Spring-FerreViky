package com.ecommerce.FerreViky.service;

import com.ecommerce.FerreViky.dto.carrito.CarritoDTO;
import com.ecommerce.FerreViky.dto.carrito.CarritoDTO.AgregarCarrito;
import com.ecommerce.FerreViky.exceptions.carrito.CarritoExceptions;
import com.ecommerce.FerreViky.exceptions.cliente.ClienteExceptions;
import com.ecommerce.FerreViky.models.Carrito;
import com.ecommerce.FerreViky.models.CarritoProducto;
import com.ecommerce.FerreViky.models.Cliente;
import com.ecommerce.FerreViky.models.Producto;
import com.ecommerce.FerreViky.repository.CarritoRepository;
import com.ecommerce.FerreViky.repository.ClienteRepository;
import com.ecommerce.FerreViky.repository.ProductoRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CarritoService - Pruebas unitarias")
class CarritoServiceTest {

    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private CarritoRepository carritoRepository;
    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private CarritoService carritoService;

    private final Faker faker = new Faker();

    private Producto generarProductoFake() {
        Producto p = new Producto();
        p.setId(faker.number().randomNumber());
        p.setCodigo(faker.code().ean8());
        p.setDescripcion(faker.commerce().productName());
        p.setMarca(faker.company().name());
        p.setUnidad("Pieza");
        p.setPrecioPublicoIva(BigDecimal.valueOf(faker.number().randomDouble(2, 10, 500)));
        p.setStock((int) faker.number().numberBetween(1L, 100L));
        return p;
    }

    private Cliente generarClienteFake() {
        Cliente c = new Cliente();
        c.setId(faker.number().randomNumber());
        c.setEmail(faker.internet().safeEmailAddress());
        c.setPassword("");
        return c;
    }

    private CarritoProducto generarProductosEnCarritoFake(Carrito carrito){
        CarritoProducto c = new CarritoProducto();
        c.setCarrito(carrito);
        c.setCantidad(faker.number().randomDigit());
        c.setProducto(generarProductoFake());
        c.setId(faker.number().randomNumber());
        return c;
    }

    private Carrito generarCarritoFake(){
        Carrito c = new Carrito();
        c.setCliente(generarClienteFake());
        c.setProductos(List.of(generarProductosEnCarritoFake(c)));
        c.setId(faker.number().randomNumber());
        return c;
    }


    @Test
    public void deberiaEncontrarCarrito(){
        Cliente cliente = new Cliente(1L, "nathan@gmail.com", "1234");

        Carrito carrito = new Carrito();
        carrito.setId(1L);
        carrito.setCliente(cliente);
        carrito.setProductos(new ArrayList<>());
        carrito.setFechaCreacion(LocalDateTime.now());

        when(carritoRepository.findByClienteId(cliente.getId())).thenReturn(Optional.of(carrito));

        CarritoDTO.CarritoResponseDTO resultado = carritoService.obtenerCarritoPorId(cliente.getId());

        assertNotNull(resultado);
        assertEquals(carrito.getId(), resultado.id());
    }

    @Test
    public void deberiaLanzarExcepcionSiCarritoNoExiste(){
        when(carritoRepository.findByClienteId(99L)).thenReturn(Optional.empty());

        assertThrows(
                CarritoExceptions.CarritoNoEncontrado.class,
                () -> carritoService.obtenerCarritoPorId(99L)
        );
    }

    @Test
    public void deberiaLanzarClienteNoEncontradoException(){
        Producto producto = generarProductoFake();
        Cliente cliente = generarClienteFake();
        AgregarCarrito carrito = new AgregarCarrito(cliente.getId(), producto.getId(),producto.getStock()-1);
        when(productoRepository.findById(producto.getId())).thenReturn(Optional.of(producto));
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.empty());

        assertThrows(
                ClienteExceptions.ClienteNoEncontradoException.class,
                () -> carritoService.agregarOActualizar(carrito)
        );
    }

    @Test
    public void deberiaLanzarCantidadExcedidaException(){
        Producto producto = generarProductoFake();
        Cliente cliente = generarClienteFake();
        AgregarCarrito carrito = new AgregarCarrito(cliente.getId(), producto.getId(),producto.getStock()+2);
        when(productoRepository.findById(producto.getId())).thenReturn(Optional.of(producto));

        assertThrows(
                CarritoExceptions.CantidadExcedidaException.class,
                () -> carritoService.agregarOActualizar(carrito)
        );
    }

    @Test
    public void deberiaLanzarCantidadNoValida(){
        Producto producto = generarProductoFake();
        Cliente cliente = generarClienteFake();
        AgregarCarrito carrito = new AgregarCarrito(cliente.getId(), producto.getId(),0);
        when(productoRepository.findById(producto.getId())).thenReturn(Optional.of(producto));

        assertThrows(
                CarritoExceptions.CantidadNoValida.class,
                () -> carritoService.agregarOActualizar(carrito)
        );
    }

    @Test
    public void deberiaLanzarCantidadExcedidaExceptionCuandoExisteElCarrito() {
        Producto producto = generarProductoFake();
        Cliente cliente = generarClienteFake();

        Carrito carrito = new Carrito();
        carrito.setId(faker.number().randomNumber());
        carrito.setCliente(cliente);


        CarritoProducto itemExistente = new CarritoProducto();
        itemExistente.setProducto(producto);
        itemExistente.setCantidad(producto.getStock());
        itemExistente.setProducto(producto);// ya está al límite
        carrito.setProductos(new ArrayList<>(List.of(itemExistente)));

        AgregarCarrito dto = new AgregarCarrito(cliente.getId(), producto.getId(), 2);

        when(productoRepository.findById(producto.getId())).thenReturn(Optional.of(producto));
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        when(carritoRepository.findByClienteConProductos(cliente)).thenReturn(Optional.of(carrito));

        assertThrows(
                CarritoExceptions.CantidadExcedidaException.class,
                () -> carritoService.agregarOActualizar(dto)
        );
    }


    @Test
    public void deberiaActualizarCantidadCuandoProductoYaEstaEnCarrito() {
        Producto producto = generarProductoFake();
        producto.setStock(50);

        Cliente cliente = generarClienteFake();

        CarritoProducto itemExistente = new CarritoProducto();
        itemExistente.setProducto(producto);
        itemExistente.setCantidad(3);

        Carrito carrito = new Carrito();
        carrito.setId(faker.number().randomNumber());
        carrito.setCliente(cliente);
        carrito.setProductos(new ArrayList<>(List.of(itemExistente)));

        AgregarCarrito dto = new AgregarCarrito(cliente.getId(), producto.getId(), 2);

        when(productoRepository.findById(producto.getId())).thenReturn(Optional.of(producto));
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        when(carritoRepository.findByClienteConProductos(cliente)).thenReturn(Optional.of(carrito));

        carritoService.agregarOActualizar(dto);

        assertEquals(5, itemExistente.getCantidad());

        verify(carritoRepository).save(carrito);
    }

    @Test
    public void deberiaAgregarProductoNuevoAlCarrito() {
        Producto producto = generarProductoFake();
        producto.setStock(50);

        Cliente cliente = generarClienteFake();

        // Carrito vacío
        Carrito carrito = new Carrito();
        carrito.setId(faker.number().randomNumber());
        carrito.setCliente(cliente);
        carrito.setProductos(new ArrayList<>());

        AgregarCarrito dto = new AgregarCarrito(cliente.getId(), producto.getId(), 2);

        when(productoRepository.findById(producto.getId())).thenReturn(Optional.of(producto));
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        when(carritoRepository.findByClienteConProductos(cliente)).thenReturn(Optional.of(carrito));

        carritoService.agregarOActualizar(dto);

        assertEquals(1, carrito.getProductos().size());
        assertEquals(2, carrito.getProductos().get(0).getCantidad());
        verify(carritoRepository).save(carrito);
    }
}