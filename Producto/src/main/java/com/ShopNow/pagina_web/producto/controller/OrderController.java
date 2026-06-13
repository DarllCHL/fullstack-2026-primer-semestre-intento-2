package com.ShopNow.pagina_web.producto.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/orders")
@Tag(name = "Órdenes", description = "Consultas entre microservicios — Producto consulta a Usuario")
@SecurityRequirement(name = "Bearer Token")
public class OrderController {

    private final RestTemplate restTemplate;

    public OrderController(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Operation(
            summary = "Verificar usuario por orden",
            description = "Consulta el microservicio de Usuario (puerto 8081) para obtener los datos de un usuario según su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Datos del usuario obtenidos exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado, token inválido o ausente"),
            @ApiResponse(responseCode = "500", description = "Error al comunicarse con el microservicio Usuario")
    })
    @GetMapping("/{id}")
    public ResponseEntity<String> checkUsuario(
            @Parameter(description = "ID del usuario a consultar", required = true, example = "1")
            @PathVariable Long id) {
        String url = "http://localhost:8081/api/v1/usuarios/" + id;
        String resultado = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(resultado);
    }
}