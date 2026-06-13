package com.ShopNow.pagina_web.producto.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
@RequestMapping("/admin/db")
@Tag(name = "Administración BD", description = "Endpoints para verificar el estado de la base de datos")
@SecurityRequirement(name = "Bearer Token")
public class DatabaseAdminController {

    @Autowired
    private DataSource dataSource;

    @Operation(
            summary = "Verificar conexión a la base de datos",
            description = "Comprueba que la conexión con la base de datos MySQL esté activa y funcionando correctamente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conexión exitosa a la base de datos"),
            @ApiResponse(responseCode = "401", description = "No autorizado, token inválido o ausente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado, se requiere rol ADMIN"),
            @ApiResponse(responseCode = "500", description = "Error al conectar con la base de datos")
    })
    @GetMapping("/check")
    public ResponseEntity<String> checkDatabase() {
        try (Connection connection = dataSource.getConnection()) {
            return ResponseEntity.ok("Conexión exitosa a: " + connection.getMetaData().getURL());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error de conexión: " + e.getMessage());
        }
    }
}