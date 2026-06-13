package com.ShopNow.pagina_web.producto.service;

import com.ShopNow.pagina_web.producto.model.Producto;
import com.ShopNow.pagina_web.producto.repository.ProductoRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepositoryJpa repositorio;

    public List<Producto> obtenerProductos() {
        return repositorio.findAll();
    }

    public Producto buscarPorId(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Producto con ID " + id + " no encontrado"));
    }

    public Producto guardar(Producto producto) {
        return repositorio.save(producto);
    }

    public Producto actualizar(Long id, Producto producto) {
        Producto existente = buscarPorId(id);
        existente.setNombre(producto.getNombre());
        existente.setDescripcion(producto.getDescripcion());
        existente.setPrecio(producto.getPrecio());
        existente.setStock(producto.getStock());
        existente.setActivo(producto.getActivo());
        return repositorio.save(existente);
    }

    public boolean eliminar(Long id) {
        buscarPorId(id);
        repositorio.deleteById(id);
        return true;
    }
}