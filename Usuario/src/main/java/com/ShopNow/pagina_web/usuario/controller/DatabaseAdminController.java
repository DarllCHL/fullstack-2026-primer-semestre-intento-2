package com.ShopNow.pagina_web.usuario.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.flywaydb.core.Flyway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/db")
@Tag(name = "Administración BD", description = "Endpoints para administración de la base de datos")
@SecurityRequirement(name = "Bearer Token")
public class DatabaseAdminController {

    private final Flyway flyway;

    public DatabaseAdminController(Flyway flyway) {
        this.flyway = flyway;
    }

    @Operation(
            summary = "Reparar historial de Flyway",
            description = "Limpia las entradas fallidas en la tabla flyway_schema_history para permitir reintentar migraciones"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historial de Flyway reparado exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado, token inválido o ausente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado, se requiere rol ADMIN"),
            @ApiResponse(responseCode = "500", description = "Error al reparar el historial")
    })
    @PostMapping("/repair")
    public ResponseEntity<String> repairDatabase() {
        flyway.repair();
        return ResponseEntity.ok("Historial de Flyway reparado. Ya puedes reintentar la migración.");
    }
}