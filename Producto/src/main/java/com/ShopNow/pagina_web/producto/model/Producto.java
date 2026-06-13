package com.ShopNow.pagina_web.producto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un producto en la tienda ShopNow")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del producto", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre del producto", example = "Laptop Gaming",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Column(length = 500)
    @Schema(description = "Descripción detallada del producto", example = "Laptop con RTX 4060 y 16GB RAM")
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "Precio del producto", example = "999.99",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Double precio;

    @Column(nullable = false)
    @Schema(description = "Stock disponible del producto", example = "50",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer stock;

    @Column(nullable = false)
    @Schema(description = "Indica si el producto está activo", example = "true")
    private Boolean activo = true;
}