package com.ShopNow.pagina_web.usuario.service;

import com.ShopNow.pagina_web.usuario.dto.UsuarioDTO;
import com.ShopNow.pagina_web.usuario.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CargaMasivaUsuarioService {


    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void procesarCarga(List<UsuarioDTO> listaDto) {
        int batchSize = 50;

        for (int i = 0; i < listaDto.size(); i++) {
            UsuarioDTO dto = listaDto.get(i);
            
            Usuario usuario = new Usuario();
            usuario.setNombre(dto.getNombre());
            usuario.setApellido(dto.getApellido());
            usuario.setEmail(dto.getEmail());
            usuario.setTelefono(dto.getTelefono());
            usuario.setDireccion(dto.getDireccion());

            entityManager.persist(usuario);

            // Cada 50 registros, enviamos a la BD y limpiamos RAM
            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }
}


