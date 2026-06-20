package com.ShopNow.pagina_web.producto.controller;

import com.ShopNow.pagina_web.producto.cliente.UsuarioClient;
import com.ShopNow.pagina_web.producto.model.Producto;
import com.ShopNow.pagina_web.producto.service.ProductoService;
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
@RequestMapping("/api/v1/productos")
@SecurityRequirement(name = "Bearer Token")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @Autowired
    private UsuarioClient usuarioClient;

    @Tag(name = "👤 Cliente", description = "Endpoints disponibles para usuarios con rol ROLE_USER y ROLE_ADMIN")
    @Operation(summary = "Obtener todos los productos", description = "Retorna la lista completa de productos activos disponibles en la tienda")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado, token inválido o ausente")
    })
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerProductos() {
        return ResponseEntity.ok(service.obtenerProductos());
    }

    @Tag(name = "👤 Cliente", description = "Endpoints disponibles para usuarios con rol ROLE_USER y ROLE_ADMIN")
    @Operation(summary = "Buscar producto por ID", description = "Retorna un producto según su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(
            @Parameter(description = "ID del producto a buscar", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Tag(name = "👤 Cliente", description = "Endpoints disponibles para usuarios con rol ROLE_USER y ROLE_ADMIN")
    @Operation(summary = "Obtener usuario asociado al producto", description = "Consulta el microservicio Usuario para obtener el usuario asociado al producto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto o usuario no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @GetMapping("/{id}/usuario")
    public ResponseEntity<String> getUsuarioDeProducto(
            @Parameter(description = "ID del producto", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(usuarioClient.getUsuario(id));
    }

    @Tag(name = "🔑 Administrador", description = "Endpoints exclusivos para usuarios con rol ROLE_ADMIN")
    @Operation(summary = "Crear un nuevo producto", description = "Guarda un producto en la base de datos. Solo ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado, se requiere rol ADMIN")
    })
    @PostMapping
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto) {
        return ResponseEntity.ok(service.guardar(producto));
    }

    @Tag(name = "🔑 Administrador", description = "Endpoints exclusivos para usuarios con rol ROLE_ADMIN")
    @Operation(summary = "Actualizar un producto", description = "Modifica los datos de un producto existente. Solo ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado, se requiere rol ADMIN")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(
            @Parameter(description = "ID del producto a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @RequestBody Producto producto) {
        return ResponseEntity.ok(service.actualizar(id, producto));
    }

    @Tag(name = "🔑 Administrador", description = "Endpoints exclusivos para usuarios con rol ROLE_ADMIN")
    @Operation(summary = "Eliminar un producto", description = "Elimina un producto de la base de datos. Solo ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado, se requiere rol ADMIN")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> eliminar(
            @Parameter(description = "ID del producto a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(service.eliminar(id));
    }
}