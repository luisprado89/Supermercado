package com.supermercado.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class DetalleVentaResponse {

    private Long productoId; // El ID del producto
    /**
     * Renormalización o Añadir datos extra
     * En la base de datos, en la tabla 'detalle_venta', solo se guarda el 'producto_id'
     * El nombre del producto esta en la Entidad o tabla Producto, pero el Service, antes de devolver la respuesta,
     * hacemos un JOIN o buscamos el producto, sacamos su nombre y lo pegamos aquí, para hacerle la vida más fácil
     * al que consume nuestra API.
     */
    private String nombreProducto;
    private Integer cantidad; // La cantidad que compraron de este producto;
    private BigDecimal precioUnitario; // Precio que tenia en ese momento de la venta
    private BigDecimal subtotal; // El resultado de multiplicar cantidad * precioUnitario
}
