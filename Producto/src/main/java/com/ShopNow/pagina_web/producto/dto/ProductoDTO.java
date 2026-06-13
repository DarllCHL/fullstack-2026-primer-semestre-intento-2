package com.ShopNow.pagina_web.producto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Objeto de transferencia para la carga masiva de productos")
public class ProductoDTO {

    @Schema(description = "Nombre del producto", example = "Laptop Gaming", required = true)
    private String nombre;

    @Schema(description = "Descripción detallada del producto", example = "Laptop con RTX 4060 y 16GB RAM")
    private String descripcion;

    @Schema(description = "Precio del producto", example = "999.99", required = true)
    private Double precio;

    @Schema(description = "Stock disponible del producto", example = "50", required = true)
    private Integer stock;

    @Schema(description = "Indica si el producto está activo", example = "true")
    private Boolean activo;
}