package com.ShopNow.pagina_web.usuario.repository;

import com.ShopNow.pagina_web.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositoryJpa extends JpaRepository<Usuario, Long> {
    Usuario findByNombre(String nombre);
}