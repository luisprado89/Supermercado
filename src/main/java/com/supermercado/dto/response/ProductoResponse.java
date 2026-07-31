package com.supermercado.dto.response;

import com.supermercado.enums.CategoriaProducto;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductoResponse {

    private Long id; // El ID del producto
    private String nombre; // El nombre del producto
    private BigDecimal precio; // El precio (BigDecimal por la regla del dinero)
    /**
     * En Java esto es un Objeto complejo (CategoriaProducto.ALIMENTOS).
     * Pero en Spring Boot es inteligente: cuando arma el JSON, No lo envía como un objeto raro, lo convierte automáticamente
     * a su nombre de texto: 'ALIMENTOS'. Así el usuario ve un string limpio en Postman.
     */
    private CategoriaProducto categoria;
    /**
     * En el Request no estaba, porque cuando creas un producto, siempre nace activo.
     * En el Response Sí está, para que el frontend (o Postman) sepa que si este producto sigue a la venta o si fue
     * 'eliminado' (borrado logico)
     */
    private Boolean activo;
}
