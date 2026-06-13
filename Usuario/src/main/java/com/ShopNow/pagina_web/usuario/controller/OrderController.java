package com.ShopNow.pagina_web.usuario.controller;

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
@Tag(name = "Órdenes", description = "Consultas entre microservicios — Usuario consulta a Producto")
@SecurityRequirement(name = "Bearer Token")
public class OrderController {

    private final RestTemplate restTemplate;

    public OrderController(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Operation(
            summary = "Verificar producto por orden",
            description = "Consulta el microservicio de Producto (puerto 8082) para obtener los datos de un producto según su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Datos del producto obtenidos exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado, token inválido o ausente"),
            @ApiResponse(responseCode = "500", description = "Error al comunicarse con el microservicio Producto")
    })
    @GetMapping("/{id}")
    public ResponseEntity<String> checkProducto(
            @Parameter(description = "ID del producto a consultar", required = true, example = "1")
            @PathVariable Long id) {
        String url = "http://localhost:8082/api/v1/productos/" + id;
        String resultado = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(resultado);
    }
}