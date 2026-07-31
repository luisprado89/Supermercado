package com.supermercado.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class VentaResponse {
    private  Long id; // Seria el número de boleta/ticket de la venta
    private LocalDate fecha; // Como usamos LocalDate en la Entidad, Aquí Spring Boot lo convierte automáticamente '2025-12-01'.
    private Long sucursalId; // El ID de la sucursal donde se hizo la venta
    /**
     * 'ENRIQUECIMIENTO DE DATOS' (Data Enrichment)
     * En la base de datos, en la tabla 'venta', SOLO se guarda el 'sucursal_id'.
     * El nombre de la sucursal vive en la tabla 'Sucursal'.
     * Pero en el Service, antes de devolver la respuesta, hacemos un JOIN o buscamos la sucursal, sacamos su nombre
     * y lo pegamos aquí. Para que el frontend no tenga que hacer dos peticiones (una para la venta y otra para saber
     * el nombre de la sucursal). Le damos todo resuelto en un solo JSON
     */
    private String nombreSucursal;
    private BigDecimal total; // La suma total de todos los subtotales de la venta
    // Esta sería una lista de otro Response, esto conecta directamente con DetalleVEntaResponse.
    private List<DetalleVentaResponse> detalle;
    // El estado del registro para nuestro borrado lógico
    // true = La venta sigue abierta/visible en el sistema.
    // false = La venta fue 'eliminada' (cerrada) por un administrador.
    private Boolean activo;
}
