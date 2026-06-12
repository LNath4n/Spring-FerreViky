package com.ecommerce.FerreViky.service;

import com.ecommerce.FerreViky.dto.producto.ProductoDTO.ProductoResponse;
import com.ecommerce.FerreViky.exceptions.productos.ProductosExceptions;
import com.ecommerce.FerreViky.models.Producto;
import com.ecommerce.FerreViky.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;


}