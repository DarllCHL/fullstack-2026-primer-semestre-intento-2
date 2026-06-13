package com.ShopNow.pagina_web.usuario.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Tag(name = "Administración", description = "Endpoints exclusivos para usuarios con rol ADMIN")
@SecurityRequirement(name = "Bearer Token")
public class AdminController {

    @Operation(
            summary = "Panel de administración",
            description = "Endpoint de acceso exclusivo para administradores del sistema. Requiere rol ADMIN."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Acceso concedido"),
            @ApiResponse(responseCode = "401", description = "No autorizado, token inválido o ausente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado, se requiere rol ADMIN")
    })
    @GetMapping("/test")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("Bienvenido al panel de administración de ShopNow");
    }
}