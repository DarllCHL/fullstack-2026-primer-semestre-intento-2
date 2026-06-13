package com.ShopNow.pagina_web.usuario.controller;

import com.ShopNow.pagina_web.usuario.dto.UsuarioDTO;
import com.ShopNow.pagina_web.usuario.service.CargaMasivaUsuarioService;
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
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Operaciones CRUD para la gestión de usuarios")
@SecurityRequirement(name = "Bearer Token")
public class CargaUsuarioController {

    @Autowired
    private CargaMasivaUsuarioService service;

    @Operation(
            summary = "Carga masiva de usuarios",
            description = "Permite insertar múltiples usuarios en una sola petición. Retorna la cantidad procesada y el tiempo de ejecución."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuarios cargados exitosamente"),
            @ApiResponse(responseCode = "400", description = "Lista vacía o datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado, token inválido o ausente"),
            @ApiResponse(responseCode = "500", description = "Error interno durante la carga")
    })
    @PostMapping("/masivo")
    public ResponseEntity<?> cargar(@RequestBody List<UsuarioDTO> usuarios) {
        try {
            if (usuarios == null || usuarios.isEmpty()) {
                return ResponseEntity.badRequest().body("La lista está vacía");
            }
            long inicio = System.currentTimeMillis();
            service.procesarCarga(usuarios);
            long fin = System.currentTimeMillis();
            return ResponseEntity.ok("Éxito: " + usuarios.size() + " procesados en " + (fin - inicio) + "ms");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en la carga: " + e.getMessage());
        }
    }
}