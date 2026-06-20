package com.ShopNow.pagina_web.usuario.assemblers;

import com.ShopNow.pagina_web.usuario.controller.UsuarioControllerV2;
import com.ShopNow.pagina_web.usuario.model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioControllerV2.class).buscarPorId(usuario.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).obtenerUsuarios()).withRel("usuarios")
        );
    }
}