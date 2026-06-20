package com.ShopNow.pagina_web;

import com.ShopNow.pagina_web.producto.model.Producto;
import com.ShopNow.pagina_web.producto.repository.ProductoRepositoryJpa;
import com.ShopNow.pagina_web.producto.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductoServiceTest {

    // Inyecta el servicio real que vamos a probar
    @Autowired
    private ProductoService productoService;

    // Crea un mock del repositorio para simular la BD
    @MockBean
    private ProductoRepositoryJpa productoRepository;

    @Test
    public void testObtenerProductos() {
        // 1. Configuramos el mock: cuando llame findAll(), devuelve esta lista
        when(productoRepository.findAll())
                .thenReturn(List.of(new Producto(1L, "Laptop", "Laptop Gaming", 999.99, 10, true)));

        // 2. Llamamos al método del servicio
        List<Producto> productos = productoService.obtenerProductos();

        // 3. Verificamos que la lista no sea nula y tenga exactamente 1 elemento
        assertNotNull(productos);
        assertEquals(1, productos.size());
    }

    @Test
    public void testBuscarPorId() {
        // 1. Mock: cuando busque el ID 1, devuelve este producto
        Producto mockProducto = new Producto(1L, "Laptop", "Laptop Gaming", 999.99, 10, true);
        when(productoRepository.findById(1L))
                .thenReturn(Optional.of(mockProducto));

        // 2. Llamamos al servicio
        Producto resultado = productoService.buscarPorId(1L);

        // 3. Verificamos que el producto encontrado sea el correcto
        assertNotNull(resultado);
        assertEquals("Laptop", resultado.getNombre());
    }

    @Test
    public void testGuardar() {
        // 1. Mock: cuando guarde este producto, lo devuelve con ID asignado
        Producto nuevo = new Producto(null, "Mouse", "Mouse Gamer", 29.99, 50, true);
        Producto guardado = new Producto(2L, "Mouse", "Mouse Gamer", 29.99, 50, true);
        when(productoRepository.save(nuevo)).thenReturn(guardado);

        // 2. Llamamos al servicio
        Producto resultado = productoService.guardar(nuevo);

        // 3. Verificamos que se guardó con ID
        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
    }

    @Test
    public void testEliminar() {
        // 1. Mock: el producto con ID 1 existe
        Producto mockProducto = new Producto(1L, "Laptop", "Laptop Gaming", 999.99, 10, true);
        when(productoRepository.findById(1L))
                .thenReturn(Optional.of(mockProducto));

        // 2. Llamamos al servicio
        boolean resultado = productoService.eliminar(1L);

        // 3. Verificamos que devolvió true
        assertTrue(resultado);
    }
}