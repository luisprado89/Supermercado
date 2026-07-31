package com.supermercado.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Builder
public class ProductoMasVendidoResponse {

    private Long productoId; // Identificador del producto más vendido
    private String nombreProducto; // El nombre del producto para que el JSON sea legible para el usuario
    /**
     * La suma TOTAL de unidades vendidas de este producto en toda la historia.
     * Usamos 'Long' en vez de 'Integer' aquí, por en 'Integer' en Java tiene un límite máximo de aprox 2.1 billones (2,147,483,647).
     * Si fuera un supermercado gigante con millones de ventas, un 'Integer' podría desbordarse y dar un error feo. Usar 'Long'
     * (que soporta hasta 9 trillones) es una buena práctica cuando sabes que vas a SUMAR muchas cantidades.
     */
    private Long totalCantidadVendida;
    private BigDecimal totalIngresos; // Suma total de dinero que ha generado ese producto(cantidad * precio en cada venta)
}
