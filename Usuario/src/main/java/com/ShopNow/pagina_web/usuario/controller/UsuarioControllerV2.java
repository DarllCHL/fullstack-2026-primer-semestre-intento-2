package com.ShopNow.pagina_web.usuario.controller;

import com.ShopNow.pagina_web.usuario.assemblers.UsuarioModelAssembler;
import com.ShopNow.pagina_web.usuario.model.Usuario;
import com.ShopNow.pagina_web.usuario.service.UsuarioService;
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
@RequestMapping("/api/v2/usuarios")
@SecurityRequirement(name = "Bearer Token")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService service;

    @Autowired
    private UsuarioModelAssembler assembler;

    @Tag(name = "👤 Cliente", description = "Endpoints disponibles para usuarios con rol ROLE_USER y ROLE_ADMIN")
    @Operation(summary = "[V2] Obtener todos los usuarios", description = "Retorna la lista completa de usuarios con enlaces HATEOAS")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado, token inválido o ausente")
    })
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Usuario>> obtenerUsuarios() {
        List<EntityModel<Usuario>> usuarios = service.obtenerUsuarios().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioControllerV2.class).obtenerUsuarios()).withSelfRel());
    }

    @Tag(name = "👤 Cliente", description = "Endpoints disponibles para usuarios con rol ROLE_USER y ROLE_ADMIN")
    @Operation(summary = "[V2] Buscar usuario por ID", description = "Retorna un usuario según su ID con enlaces HATEOAS")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Usuario> buscarPorId(
            @Parameter(description = "ID del usuario a buscar", required = true, example = "1")
            @PathVariable Long id) {
        Usuario usuario = service.buscarPorId(id);
        return assembler.toModel(usuario);
    }

    @Tag(name = "🔑 Administrador", description = "Endpoints exclusivos para usuarios con rol ROLE_ADMIN")
    @Operation(summary = "[V2] Crear un nuevo usuario", description = "Guarda un usuario y retorna el recurso creado con enlaces HATEOAS. Solo ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado, se requiere rol ADMIN")
    })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Usuario> guardar(@RequestBody Usuario usuario) {
        return assembler.toModel(service.guardar(usuario));
    }

    @Tag(name = "🔑 Administrador", description = "Endpoints exclusivos para usuarios con rol ROLE_ADMIN")
    @Operation(summary = "[V2] Actualizar un usuario", description = "Modifica los datos de un usuario y retorna el recurso actualizado con enlaces HATEOAS. Solo ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado, se requiere rol ADMIN")
    })
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Usuario> actualizar(
            @Parameter(description = "ID del usuario a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @RequestBody Usuario usuario) {
        return assembler.toModel(service.actualizar(id, usuario));
    }

    @Tag(name = "🔑 Administrador", description = "Endpoints exclusivos para usuarios con rol ROLE_ADMIN")
    @Operation(summary = "[V2] Eliminar un usuario", description = "Elimina un usuario y retorna confirmación. Solo ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado, se requiere rol ADMIN")
    })
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Boolean> eliminar(
            @Parameter(description = "ID del usuario a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(service.eliminar(id));
    }
}