package com.ShopNow.pagina_web.producto.repository;

import com.ShopNow.pagina_web.producto.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepositoryJpa extends JpaRepository<Producto, Long> {
    Producto findByNombre(String nombre);
    Optional<Producto> findTopByOrderByIdDesc();
}