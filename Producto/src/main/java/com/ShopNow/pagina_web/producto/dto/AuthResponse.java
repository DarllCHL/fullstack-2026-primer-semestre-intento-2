package com.ShopNow.pagina_web.producto.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de autenticación que contiene el token JWT")
public class AuthResponse {

    @Schema(description = "Token JWT para usar en los demás endpoints como Bearer Token",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    public AuthResponse(String token) { this.token = token; }
    public String getToken() { return token; }
}