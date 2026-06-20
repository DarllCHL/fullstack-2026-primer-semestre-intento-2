package com.ShopNow.pagina_web.producto.service;

import com.ShopNow.pagina_web.producto.dto.ProductoDTO;
import com.ShopNow.pagina_web.producto.model.Producto;
import com.ShopNow.pagina_web.producto.repository.ProductoRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CargaMasivaProductoService {

    @Autowired
    private ProductoRepositoryJpa repositorio;

    @Transactional
    public void procesarCarga(List<ProductoDTO> productosDTO) {
        List<Producto> productos = productosDTO.stream()
                .map(dto -> {
                    Producto producto = new Producto();
                    producto.setNombre(dto.getNombre());
                    producto.setDescripcion(dto.getDescripcion());
                    producto.setPrecio(dto.getPrecio());
                    producto.setStock(dto.getStock());
                    producto.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
                    return producto;
                })
                .collect(Collectors.toList());

        repositorio.saveAll(productos);
    }

    @Transactional
    public int procesarCargaAuto() {
        Long ultimoId = repositorio.findTopByOrderByIdDesc()
                .map(Producto::getId)
                .orElse(0L);

        List<Producto> productos = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            long numero = ultimoId + i;
            Producto p = new Producto();
            p.setNombre("Producto " + numero);
            p.setDescripcion("Descripción genérica del producto número " + numero);
            p.setPrecio(Math.round((10.99 + numero * 5.5) * 100.0) / 100.0);
            p.setStock((int)(numero * 3) % 200 + 10);
            p.setActivo(true);
            productos.add(p);
        }

        repositorio.saveAll(productos);
        return productos.size();
    }
}