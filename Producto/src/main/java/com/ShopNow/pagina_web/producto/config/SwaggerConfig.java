package com.ShopNow.pagina_web.producto.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ShopNow - Microservicio Producto")
                        .description("API REST para gestión de productos de la tienda ShopNow")
                        .version("1.0.0"))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Token"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Token", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .tags(List.of(
                        new Tag().name("🔓 Autenticación")
                                .description("Endpoints públicos de acceso al sistema")
                                .extensions(java.util.Map.of("x-order", 2)),
                        new Tag().name("👤 Cliente")
                                .description("Endpoints disponibles para usuarios con rol ROLE_USER y ROLE_ADMIN")
                                .extensions(java.util.Map.of("x-order", 3)),
                        new Tag().name("🔑 Administrador")
                                .description("Endpoints exclusivos para usuarios con rol ROLE_ADMIN")
                                .extensions(java.util.Map.of("x-order", 4))
                ));
    }
}