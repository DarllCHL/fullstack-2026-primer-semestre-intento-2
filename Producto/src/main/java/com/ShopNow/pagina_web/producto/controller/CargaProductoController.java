package com.ShopNow.pagina_web.producto.controller;

import com.ShopNow.pagina_web.producto.dto.ProductoDTO;
import com.ShopNow.pagina_web.producto.service.CargaMasivaProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "🔑 Administrador", description = "Endpoints exclusivos para usuarios con rol ROLE_ADMIN")
@SecurityRequirement(name = "Bearer Token")
public class CargaProductoController {

    @Autowired
    private CargaMasivaProductoService service;

    @Operation(
            summary = "Carga masiva de productos",
            description = "Permite insertar múltiples productos en una sola petición. Retorna la cantidad procesada y el tiempo de ejecución. Solo ADMIN."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Productos cargados exitosamente"),
            @ApiResponse(responseCode = "400", description = "Lista vacía o datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado, token inválido o ausente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado, se requiere rol ADMIN"),
            @ApiResponse(responseCode = "500", description = "Error interno durante la carga")
    })
    @PostMapping("/masivo")
    public ResponseEntity<?> cargar(@RequestBody List<ProductoDTO> productos) {
        try {
            if (productos == null || productos.isEmpty()) {
                return ResponseEntity.badRequest().body("La lista está vacía");
            }
            long inicio = System.currentTimeMillis();
            service.procesarCarga(productos);
            long fin = System.currentTimeMillis();
            return ResponseEntity.ok("Éxito: " + productos.size() + " procesados en " + (fin - inicio) + "ms");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en la carga: " + e.getMessage());
        }
    }
}