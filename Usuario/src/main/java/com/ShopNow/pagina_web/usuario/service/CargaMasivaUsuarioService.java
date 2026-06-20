package com.ShopNow.pagina_web.usuario.service;

import com.ShopNow.pagina_web.usuario.dto.UsuarioDTO;
import com.ShopNow.pagina_web.usuario.model.Usuario;
import com.ShopNow.pagina_web.usuario.repository.UsuarioRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CargaMasivaUsuarioService {

    @Autowired
    private UsuarioRepositoryJpa repositorio;

    @Transactional
    public void procesarCarga(List<UsuarioDTO> listaDto) {
        List<Usuario> usuarios = new ArrayList<>();
        for (UsuarioDTO dto : listaDto) {
            Usuario usuario = new Usuario();
            usuario.setNombre(dto.getNombre());
            usuario.setApellido(dto.getApellido());
            usuario.setEmail(dto.getEmail());
            usuario.setTelefono(dto.getTelefono());
            usuario.setDireccion(dto.getDireccion());
            usuarios.add(usuario);
        }
        repositorio.saveAll(usuarios);
    }

    @Transactional
    public int procesarCargaAuto() {
        Long ultimoId = repositorio.findTopByOrderByIdDesc()
                .map(Usuario::getId)
                .orElse(0L);

        List<Usuario> usuarios = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            long numero = ultimoId + i;
            Usuario u = new Usuario();
            u.setNombre("Nombre" + numero);
            u.setApellido("Apellido" + numero);
            u.setEmail("usuario" + numero + "@shopnow.cl");
            u.setTelefono("+5691" + String.format("%07d", numero));
            u.setDireccion("Dirección genérica " + numero + ", Santiago");
            usuarios.add(u);
        }

        repositorio.saveAll(usuarios);
        return usuarios.size();
    }
}