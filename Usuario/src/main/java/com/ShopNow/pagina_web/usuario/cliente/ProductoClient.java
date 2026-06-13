package com.ShopNow.pagina_web.usuario.cliente;

import com.ShopNow.pagina_web.usuario.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "producto-service", url = "http://localhost:8082", configuration = FeignConfig.class)
public interface ProductoClient {
    @GetMapping("/api/v1/productos/{id}")
    String getProducto(@PathVariable("id") Long id);
}