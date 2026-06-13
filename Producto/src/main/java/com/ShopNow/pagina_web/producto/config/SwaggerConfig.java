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
                        new Tag().name("Administración").description("Endpoints exclusivos para usuarios con rol ADMIN"),
                        new Tag().name("Órdenes").description("Consultas entre microservicios — Producto consulta a Usuario"),
                        new Tag().name("Autenticación").description("Endpoints para login y registro de usuarios del sistema"),
                        new Tag().name("Productos").description("Operaciones CRUD para la gestión de productos"),
                        new Tag().name("Administración BD").description("Endpoints para verificar el estado de la base de datos")
                ));
    }
}