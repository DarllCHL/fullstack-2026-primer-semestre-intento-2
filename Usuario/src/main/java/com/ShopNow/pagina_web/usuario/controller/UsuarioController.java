package com.ShopNow.pagina_web.usuario.controller;

import com.ShopNow.pagina_web.usuario.cliente.ProductoClient;
import com.ShopNow.pagina_web.usuario.model.Usuario;
import com.ShopNow.pagina_web.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@SecurityRequirement(name = "Bearer Token")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private ProductoClient productoClient;

    @Tag(name = "👤 Cliente", description = "Endpoints disponibles para usuarios con rol ROLE_USER y ROLE_ADMIN")
    @Operation(summary = "Obtener todos los usuarios", description = "Retorna la lista completa de usuarios registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado, token inválido o ausente")
    })
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        return ResponseEntity.ok(service.obtenerUsuarios());
    }

    @Tag(name = "👤 Cliente", description = "Endpoints disponibles para usuarios con rol ROLE_USER y ROLE_ADMIN")
    @Operation(summary = "Buscar usuario por ID", description = "Retorna un usuario según su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(
            @Parameter(description = "ID del usuario a buscar", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Tag(name = "👤 Cliente", description = "Endpoints disponibles para usuarios con rol ROLE_USER y ROLE_ADMIN")
    @Operation(summary = "Obtener producto asociado al usuario", description = "Consulta el microservicio Producto para obtener el producto asociado al usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario o producto no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @GetMapping("/{id}/producto")
    public ResponseEntity<String> getProductoDeUsuario(
            @Parameter(description = "ID del usuario", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(productoClient.getProducto(id));
    }

    @Tag(name = "🔑 Administrador", description = "Endpoints exclusivos para usuarios con rol ROLE_ADMIN")
    @Operation(summary = "Crear un nuevo usuario", description = "Guarda un usuario en la base de datos. Solo ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado, se requiere rol ADMIN")
    })
    @PostMapping
    public ResponseEntity<Usuario> guardar(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(service.guardar(usuario));
    }

    @Tag(name = "🔑 Administrador", description = "Endpoints exclusivos para usuarios con rol ROLE_ADMIN")
    @Operation(summary = "Actualizar un usuario", description = "Modifica los datos de un usuario existente. Solo ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado, se requiere rol ADMIN")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(
            @Parameter(description = "ID del usuario a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @RequestBody Usuario usuario) {
        return ResponseEntity.ok(service.actualizar(id, usuario));
    }

    @Tag(name = "🔑 Administrador", description = "Endpoints exclusivos para usuarios con rol ROLE_ADMIN")
    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario de la base de datos según su ID. Solo ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado, se requiere rol ADMIN")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> eliminar(
            @Parameter(description = "ID del usuario a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(service.eliminar(id));
    }
}