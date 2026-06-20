package com.ShopNow.pagina_web;

import com.ShopNow.pagina_web.usuario.model.Usuario;
import com.ShopNow.pagina_web.usuario.service.UsuarioService;
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
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "USER")
    public void testObtenerUsuarios() throws Exception {
        // 1. Mock: el servicio devuelve esta lista
        when(usuarioService.obtenerUsuarios())
                .thenReturn(List.of(new Usuario(1L, "Juan", "Pérez", "juan@mail.com", "+56912345678", "Av. Siempre Viva 123")));

        // 2. Simulamos GET /api/v1/usuarios
        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testBuscarPorId() throws Exception {
        // 1. Mock: el servicio devuelve este usuario para ID 1
        when(usuarioService.buscarPorId(1L))
                .thenReturn(new Usuario(1L, "Juan", "Pérez", "juan@mail.com", "+56912345678", "Av. Siempre Viva 123"));

        // 2. Simulamos GET /api/v1/usuarios/1
        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.email").value("juan@mail.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCrearUsuario() throws Exception {
        // 1. Usuario que enviaremos en el body
        Usuario nuevo = new Usuario(null, "María", "López", "maria@mail.com", "+56987654321", "Calle Falsa 456");
        Usuario guardado = new Usuario(2L, "María", "López", "maria@mail.com", "+56987654321", "Calle Falsa 456");

        // 2. Mock: cuando guarde cualquier usuario, devuelve el guardado
        when(usuarioService.guardar(any(Usuario.class))).thenReturn(guardado);

        // 3. Simulamos POST /api/v1/usuarios
        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.nombre").value("María"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testEliminarUsuario() throws Exception {
        // 1. Mock: el servicio devuelve true al eliminar ID 1
        when(usuarioService.eliminar(1L)).thenReturn(true);

        // 2. Simulamos DELETE /api/v1/usuarios/1
        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}