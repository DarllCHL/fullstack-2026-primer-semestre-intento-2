package com.ShopNow.pagina_web;

import com.ShopNow.pagina_web.producto.model.Producto;
import com.ShopNow.pagina_web.producto.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductoControllerTest {

    // Simula peticiones HTTP sin levantar un servidor real
    @Autowired
    private MockMvc mockMvc;

    // Mock del servicio — el controller usará este en vez del real
    @MockBean
    private ProductoService productoService;

    // Convierte objetos Java a JSON y viceversa
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "USER")
    public void testObtenerProductos() throws Exception {
        // 1. Mock: el servicio devuelve esta lista
        when(productoService.obtenerProductos())
                .thenReturn(List.of(new Producto(1L, "Laptop", "Laptop Gaming", 999.99, 10, true)));

        // 2. Simulamos GET /api/v1/productos y verificamos la respuesta
        mockMvc.perform(get("/api/v1/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Laptop"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testBuscarPorId() throws Exception {
        // 1. Mock: el servicio devuelve este producto para ID 1
        when(productoService.buscarPorId(1L))
                .thenReturn(new Producto(1L, "Laptop", "Laptop Gaming", 999.99, 10, true));

        // 2. Simulamos GET /api/v1/productos/1
        mockMvc.perform(get("/api/v1/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Laptop"))
                .andExpect(jsonPath("$.precio").value(999.99));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCrearProducto() throws Exception {
        // 1. Producto que vamos a enviar en el body
        Producto nuevo = new Producto(null, "Mouse", "Mouse Gamer", 29.99, 50, true);
        Producto guardado = new Producto(2L, "Mouse", "Mouse Gamer", 29.99, 50, true);

        // 2. Mock: cuando el servicio guarde cualquier producto, devuelve el guardado
        when(productoService.guardar(any(Producto.class))).thenReturn(guardado);

        // 3. Simulamos POST /api/v1/productos con el producto en JSON
        mockMvc.perform(post("/api/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.nombre").value("Mouse"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testEliminarProducto() throws Exception {
        // 1. Mock: el servicio devuelve true al eliminar ID 1
        when(productoService.eliminar(1L)).thenReturn(true);

        // 2. Simulamos DELETE /api/v1/productos/1
        mockMvc.perform(delete("/api/v1/productos/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}