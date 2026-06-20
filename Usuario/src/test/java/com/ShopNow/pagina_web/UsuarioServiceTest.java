package com.ShopNow.pagina_web;

import com.ShopNow.pagina_web.usuario.model.Usuario;
import com.ShopNow.pagina_web.usuario.repository.UsuarioRepositoryJpa;
import com.ShopNow.pagina_web.usuario.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepositoryJpa usuarioRepository;

    @Test
    public void testObtenerUsuarios() {
        when(usuarioRepository.findAll())
                .thenReturn(List.of(new Usuario(1L, "Juan", "Pérez", "juan@mail.com", "+56912345678", "Av. Siempre Viva 123")));

        List<Usuario> usuarios = usuarioService.obtenerUsuarios();

        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
    }

    @Test
    public void testBuscarPorId() {
        Usuario mockUsuario = new Usuario(1L, "Juan", "Pérez", "juan@mail.com", "+56912345678", "Av. Siempre Viva 123");
        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(mockUsuario));

        Usuario resultado = usuarioService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
    }

    @Test
    public void testGuardar() {
        Usuario nuevo = new Usuario(null, "María", "López", "maria@mail.com", "+56987654321", "Calle Falsa 456");
        Usuario guardado = new Usuario(2L, "María", "López", "maria@mail.com", "+56987654321", "Calle Falsa 456");
        when(usuarioRepository.save(nuevo)).thenReturn(guardado);

        Usuario resultado = usuarioService.guardar(nuevo);

        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
    }

    @Test
    public void testEliminar() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        boolean resultado = usuarioService.eliminar(1L);

        assertTrue(resultado);
    }
}