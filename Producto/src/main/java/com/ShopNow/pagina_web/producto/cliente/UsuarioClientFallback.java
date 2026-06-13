package com.ShopNow.pagina_web.producto.cliente;

import org.springframework.stereotype.Component;

@Component
public class UsuarioClientFallback implements UsuarioClient {

    @Override
    public String getUsuario(Long id) {
        return "El microservicio Usuario no está disponible en este momento";
    }
}