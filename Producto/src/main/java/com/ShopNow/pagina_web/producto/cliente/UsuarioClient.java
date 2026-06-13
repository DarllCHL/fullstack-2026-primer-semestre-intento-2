package com.ShopNow.pagina_web.producto.cliente;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "usuario-service",
        url = "http://localhost:8081",
        fallback = UsuarioClientFallback.class
)
public interface UsuarioClient {

    @Operation(summary = "Obtener usuario por ID desde microservicio Usuario")
    @GetMapping("/api/v1/usuarios/{id}")
    String getUsuario(@PathVariable Long id);
}