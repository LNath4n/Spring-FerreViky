package com.ecommerce.FerreViky.service;

import com.ecommerce.FerreViky.dto.cliente.ClienteDTO.CreacionDeClienteRespuestaDto;
import com.ecommerce.FerreViky.dto.cliente.ClienteDTO.LoginClienteDto;
import com.ecommerce.FerreViky.exceptions.carrito.CarritoExceptions;
import com.ecommerce.FerreViky.exceptions.cliente.ClienteExceptions;
import com.ecommerce.FerreViky.mapper.cliente.ClienteMappers;
import com.ecommerce.FerreViky.models.Carrito;
import com.ecommerce.FerreViky.models.Cliente;
import com.ecommerce.FerreViky.repository.CarritoRepository;
import com.ecommerce.FerreViky.repository.ClienteRepository;
import com.ecommerce.FerreViky.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMappers clienteMappers;

    @Mock
    private CarritoRepository carritoRepository;

    @InjectMocks
    private ClienteService clienteService; //Esta es la instancia a probar


    @Test
    public void login_deberiaRetornarTrue_cuandoEmailExisteYPasswordCoincide(){
        // Dado (Given)
        LoginClienteDto dto = new LoginClienteDto("tilin@gmail.com", "secreto123");
        /// Datos de entrada

        Cliente cliente = new Cliente();
        cliente.setEmail("tilin@gmail.com");
        cliente.setPassword("secreto123");
        ///El objeto que vendria de la base de datos


        // Simulas el repositorio, NO el servicio
        Mockito.when(clienteRepository.findByEmail(dto.email()))
                .thenReturn(java.util.Optional.of(cliente));
        ///Cuando alguien llame a la funcion findByEmail le devolvemos este Optional que es lo que regresa el Repository


        // Cuando (When)
        boolean resultado = clienteService.login(dto);
        /// Llamada al metodo a probar


        // Entonces (Then)
        assertTrue(resultado); ///Verifica que si se pudo logear
        Mockito.verify(clienteRepository, Mockito.times(1)).findByEmail(dto.email());
        ///Verifica que si se llamo el numero necesario de veces
    }

    @Test
    public void login_deberiaRetornarFalse_cuandoEmailExistePeroPasswordNo(){
        LoginClienteDto dto = new LoginClienteDto("tilin@gmail.com", "secreto123");

        Cliente cliente = new Cliente();
        cliente.setEmail("tilin@gmail.com");
        cliente.setPassword("123secreto");
        ///El objeto que vendria de la base de datos


        // Simulas el repositorio, NO el servicio
        Mockito.when(clienteRepository.findByEmail(dto.email()))
                .thenReturn(java.util.Optional.of(cliente));
        ///Cuando alguien llame a la funcion findByEmail le devolvemos este Optional que es lo que regresa el Repository


        // Cuando (When)
        boolean resultado = clienteService.login(dto);
        /// Llamada al metodo a probar


        // Entonces (Then)
        assertFalse(resultado); ///Verifica que si se pudo logear
        Mockito.verify(clienteRepository, Mockito.times(1)).findByEmail(dto.email());
        ///Verifica que si se llamo el numero necesario de veces
    }

    @Test
    public void login_deberiaLanzarExcepcion_cuandoEmailNoExiste(){
        LoginClienteDto dto = new LoginClienteDto("tilin@gmail.com", "secreto123");
        /// El Objeto de entrada

        Mockito.when(clienteRepository.findByEmail(dto.email()))
                .thenReturn(java.util.Optional.empty());
        /// Cuando alguien llame a FindByEmail regresa Vacio (No existe el correo)

        assertThrows(ClienteExceptions.ClienteNoEncontradoException.class, () -> {
            clienteService.login(dto);
        });

    }


    @Test
    public void guardarCliente_deberiaGuardarClienteYCarrito_cuandoEmailNoExiste() {
        // Given
        LoginClienteDto dto = new LoginClienteDto("nuevo@mail.com", "password123");

        Cliente clienteSinId = new Cliente();
        clienteSinId.setEmail(dto.email());
        clienteSinId.setPassword(dto.password());

        Cliente clienteConId = new Cliente();
        clienteConId.setId(1L);
        clienteConId.setEmail(dto.email());
        clienteConId.setPassword(dto.password());

        Carrito carritoGuardado = new Carrito();
        carritoGuardado.setId(100L);
        carritoGuardado.setCliente(clienteConId);
        carritoGuardado.setFechaCreacion(LocalDateTime.now());

        CreacionDeClienteRespuestaDto dtoEsperado = new CreacionDeClienteRespuestaDto(
                clienteConId.getId(),
                clienteConId.getEmail()
        );

        // Simulaciones
        Mockito.when(clienteRepository.existsByEmail(dto.email())).thenReturn(false);
        Mockito.when(clienteMappers.DtoLoginACliente(dto)).thenReturn(clienteSinId);
        Mockito.when(clienteRepository.save(clienteSinId)).thenReturn(clienteConId);
        Mockito.when(carritoRepository.save(Mockito.any(Carrito.class))).thenReturn(carritoGuardado);
        Mockito.when(clienteMappers.ClienteACreacion(clienteConId)).thenReturn(dtoEsperado);

        // When
        CreacionDeClienteRespuestaDto resultado = clienteService.guardarCliente(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(dtoEsperado.id(), resultado.id());
        assertEquals(dtoEsperado.email(), resultado.email());

        Mockito.verify(clienteRepository, Mockito.times(1)).existsByEmail(dto.email());
        Mockito.verify(clienteMappers, Mockito.times(1)).DtoLoginACliente(dto);
        Mockito.verify(clienteRepository, Mockito.times(1)).save(clienteSinId);
        Mockito.verify(carritoRepository, Mockito.times(1)).save(Mockito.any(Carrito.class));
        Mockito.verify(clienteMappers, Mockito.times(1)).ClienteACreacion(clienteConId);
    }


    @Test
    public void guardarCliente_deberiaLanzarExcepcion_cuandoEmailYaExiste() {
        // Given
        LoginClienteDto dto = new LoginClienteDto("existente@mail.com", "cualquierpass");

        Mockito.when(clienteRepository.existsByEmail(dto.email())).thenReturn(true);

        // When / Then
        assertThrows(ClienteExceptions.EmailYaExisteException.class, () -> {
            clienteService.guardarCliente(dto);
        });

        // Verificar que NO se llamó a los otros métodos
        Mockito.verify(clienteRepository, Mockito.times(1)).existsByEmail(dto.email());
        Mockito.verify(clienteMappers, Mockito.never()).DtoLoginACliente(Mockito.any());
        Mockito.verify(clienteRepository, Mockito.never()).save(Mockito.any());
        Mockito.verify(carritoRepository, Mockito.never()).save(Mockito.any());
        Mockito.verify(clienteMappers, Mockito.never()).ClienteACreacion(Mockito.any());
    }

    @Test
    public void guardarCliente_deberiaHacerRollback_cuandoFallaGuardarCarrito() {
        // Given
        LoginClienteDto dto = new LoginClienteDto("nuevo@mail.com", "pass");
        Cliente clienteSinId = new Cliente();
        clienteSinId.setEmail(dto.email());
        Cliente clienteConId = new Cliente();
        clienteConId.setId(1L);

        Mockito.when(clienteRepository.existsByEmail(dto.email())).thenReturn(false);
        Mockito.when(clienteMappers.DtoLoginACliente(dto)).thenReturn(clienteSinId);
        Mockito.when(clienteRepository.save(clienteSinId)).thenReturn(clienteConId);
        Mockito.when(carritoRepository.save(Mockito.any(Carrito.class)))
                .thenThrow(new RuntimeException("Error de BD"));

// When / Then
        assertThrows(CarritoExceptions.ErrorAlCrearCarritoException.class,
                () -> clienteService.guardarCliente(dto));

        // Verificar que NO se llama al mapper de respuesta (porque falló antes)
        Mockito.verify(clienteMappers, Mockito.never()).ClienteACreacion(Mockito.any());
    }
}