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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClienteService - Pruebas unitarias")
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMappers clienteMappers;

    @Mock
    private CarritoRepository carritoRepository;

    @InjectMocks
    private ClienteService clienteService;


    @Test
    @DisplayName("login() → retorna el ID cuando el email existe y el password coincide")
    public void login_deberiaRetornarId_cuandoEmailExisteYPasswordCoincide() {
        // Given
        LoginClienteDto dto = new LoginClienteDto("tilin@gmail.com", "secreto123");

        Cliente cliente = new Cliente();
        cliente.setId(42L);
        cliente.setEmail("tilin@gmail.com");
        cliente.setPassword("secreto123");

        Mockito.when(clienteRepository.findByEmail(dto.email()))
                .thenReturn(java.util.Optional.of(cliente));

        // When
        Long resultado = clienteService.login(dto);

        // Then
        assertEquals(42L, resultado);
        Mockito.verify(clienteRepository, Mockito.times(1)).findByEmail(dto.email());
    }

    @Test
    @DisplayName("login() → lanza CredencialesInvalidasException cuando el password no coincide")
    public void login_deberiaLanzarExcepcion_cuandoEmailExistePeroPasswordNo() {
        // Given
        LoginClienteDto dto = new LoginClienteDto("tilin@gmail.com", "secreto123");

        Cliente cliente = new Cliente();
        cliente.setEmail("tilin@gmail.com");
        cliente.setPassword("123secreto");

        Mockito.when(clienteRepository.findByEmail(dto.email()))
                .thenReturn(java.util.Optional.of(cliente));

        // When / Then
        assertThrows(ClienteExceptions.CredencialesInvalidasException.class, () -> {
            clienteService.login(dto);
        });

        Mockito.verify(clienteRepository, Mockito.times(1)).findByEmail(dto.email());
    }

    @Test
    @DisplayName("login() → lanza ClienteNoEncontradoException cuando el email no existe")
    public void login_deberiaLanzarExcepcion_cuandoEmailNoExiste() {
        LoginClienteDto dto = new LoginClienteDto("tilin@gmail.com", "secreto123");

        Mockito.when(clienteRepository.findByEmail(dto.email()))
                .thenReturn(java.util.Optional.empty());

        assertThrows(ClienteExceptions.ClienteNoEncontradoException.class, () -> {
            clienteService.login(dto);
        });
    }


    @Test
    @DisplayName("guardarCliente() → guarda cliente y carrito correctamente cuando el email no existe")
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
    @DisplayName("guardarCliente() → lanza EmailYaExisteException cuando el email ya está registrado")
    public void guardarCliente_deberiaLanzarExcepcion_cuandoEmailYaExiste() {
        // Given
        LoginClienteDto dto = new LoginClienteDto("existente@mail.com", "cualquierpass");

        Mockito.when(clienteRepository.existsByEmail(dto.email())).thenReturn(true);

        // When / Then
        assertThrows(ClienteExceptions.EmailYaExisteException.class, () -> {
            clienteService.guardarCliente(dto);
        });

        Mockito.verify(clienteRepository, Mockito.times(1)).existsByEmail(dto.email());
        Mockito.verify(clienteMappers, Mockito.never()).DtoLoginACliente(Mockito.any());
        Mockito.verify(clienteRepository, Mockito.never()).save(Mockito.any());
        Mockito.verify(carritoRepository, Mockito.never()).save(Mockito.any());
        Mockito.verify(clienteMappers, Mockito.never()).ClienteACreacion(Mockito.any());
    }

    @Test
    @DisplayName("guardarCliente() → lanza ErrorAlCrearCarritoException y hace rollback cuando falla guardar el carrito")
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

        Mockito.verify(clienteMappers, Mockito.never()).ClienteACreacion(Mockito.any());
    }
}