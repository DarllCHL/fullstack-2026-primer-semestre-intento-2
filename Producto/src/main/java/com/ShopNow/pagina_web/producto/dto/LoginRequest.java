package com.ShopNow.pagina_web.producto.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto de solicitud para autenticación")
public class LoginRequest {

    @Schema(description = "Nombre de usuario del sistema", example = "admin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "Contraseña del usuario", example = "1234", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    public LoginRequest() {}

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}