package com.ShopNow.pagina_web.producto.controller;

import com.ShopNow.pagina_web.producto.assemblers.ProductoModelAssembler;
import com.ShopNow.pagina_web.producto.model.Producto;
import com.ShopNow.pagina_web.producto.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/productos")
@SecurityRequirement(name = "Bearer Token")
public class ProductoControllerV2 {

    @Autowired
    private ProductoService service;

    @Autowired
    private ProductoModelAssembler assembler;

    @Tag(name = "👤 Cliente", description = "Endpoints disponibles para usuarios con rol ROLE_USER y ROLE_ADMIN")
    @Operation(summary = "[V2] Obtener todos los productos", description = "Retorna la lista completa de productos con enlaces HATEOAS")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado, token inválido o ausente")
    })
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Producto>> obtenerProductos() {
        List<EntityModel<Producto>> productos = service.obtenerProductos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerV2.class).obtenerProductos()).withSelfRel());
    }

    @Tag(name = "👤 Cliente", description = "Endpoints disponibles para usuarios con rol ROLE_USER y ROLE_ADMIN")
    @Operation(summary = "[V2] Buscar producto por ID", description = "Retorna un producto según su ID con enlaces HATEOAS")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Producto> buscarPorId(
            @Parameter(description = "ID del producto a buscar", required = true, example = "1")
            @PathVariable Long id) {
        Producto producto = service.buscarPorId(id);
        return assembler.toModel(producto);
    }

    @Tag(name = "🔑 Administrador", description = "Endpoints exclusivos para usuarios con rol ROLE_ADMIN")
    @Operation(summary = "[V2] Crear un nuevo producto", description = "Guarda un producto y retorna el recurso creado con enlaces HATEOAS. Solo ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado, se requiere rol ADMIN")
    })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Producto> guardar(@RequestBody Producto producto) {
        return assembler.toModel(service.guardar(producto));
    }

    @Tag(name = "🔑 Administrador", description = "Endpoints exclusivos para usuarios con rol ROLE_ADMIN")
    @Operation(summary = "[V2] Actualizar un producto", description = "Modifica los datos de un producto y retorna el recurso actualizado con enlaces HATEOAS. Solo ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado, se requiere rol ADMIN")
    })
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Producto> actualizar(
            @Parameter(description = "ID del producto a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @RequestBody Producto producto) {
        return assembler.toModel(service.actualizar(id, producto));
    }

    @Tag(name = "🔑 Administrador", description = "Endpoints exclusivos para usuarios con rol ROLE_ADMIN")
    @Operation(summary = "[V2] Eliminar un producto", description = "Elimina un producto y retorna confirmación. Solo ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado, se requiere rol ADMIN")
    })
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Boolean> eliminar(
            @Parameter(description = "ID del producto a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(service.eliminar(id));
    }
}