package com.supermercado.mapper;

import com.supermercado.dto.response.DetalleVentaResponse;
import com.supermercado.dto.response.VentaResponse;
import com.supermercado.entity.DetalleVenta;
import com.supermercado.entity.Venta;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper encargado de convertir la entidad {@link Venta} y sus detalles
 * en los DTOs de respuesta utilizados por la API.
 *
 * <p>La conversión de la colección de detalles se realiza mediante la API
 * de Streams de Java, transformando cada {@link DetalleVenta} en un
 * {@link DetalleVentaResponse}.</p>
 */
@Component
public class VentaMapper {

    /**
     * Convierte una entidad {@link Venta} en su correspondiente  {@link VentaResponse}.
     *
     * <p>Además de los datos propios de la venta, incluye la información de la
     * sucursal asociada y transforma la lista de detalles utilizando Streams.</p>
     *
     * @param venta entidad que se desea convertir.
     * @return DTO de respuesta con la información de la venta.
     */
    public VentaResponse toResponse(Venta venta) {
        /*
         Vamos a convertir una List<DetalleVenta> en una List<DetalleVentaResponse>.
         1. venta.getDetalles(): Obtiene la lista de entidades de la venta.
         2. .stream(): Convierte la lista normal en un "río" de datos que podemos manipular.
         3. .map(...): Es como un bucle 'for each' invisible. Toma cada DetalleVenta de la lista y lo pasa por la función que está dentro.
         4. this::toDetalleResponse: (Método de referencia). Es una forma corta de escribir una Lambda.
         Significa: "Para cada detalle, ejecuta el método 'toDetalleREsponse' que está aquí mismo en esta misma clase(this)".
         5. .collect(Collectors.toList()): Cierra el río. Toma todos los objetos que fueron saliendo del .map() y los
          empaquetas en una nueva Lista de Java.
         */
        List<DetalleVentaResponse> detallesResponse = venta.getDetalles().stream()
                .map(this::toDetalleResponse)
                .collect(Collectors.toList());
        /*
         En la Entidad Venta no tenemos un campo 'sucursalId' ni 'nombreSucursal', tenemos un objeto completo 'Sucursal sucursal',
         con relación @ManyToOne que configuramos, podemos 'navegar' con puntos:
         venta.getSucurtal() -> me da el objeto Sucursal.
         .getId() -> me da el ID de esa sucursal. Java hace el JOIN en la BD automáticamente por detrás.

         */
        return VentaResponse.builder()
                .id(venta.getId())
                .fecha(venta.getFecha())
                .sucursalId(venta.getSucursal().getId())
                .nombreSucursal(venta.getSucursal().getNombre())
                .total(venta.getTotal())
                .detalle(detallesResponse)
                .activo(venta.getActivo())
                .build();
    }

    public DetalleVentaResponse toDetalleResponse(DetalleVenta detalleVenta) {
        return DetalleVentaResponse.builder()
                .productoId(detalleVenta.getProducto().getId())
                .nombreProducto(detalleVenta.getProducto().getNombre())
                .cantidad(detalleVenta.getCantidad())
                .precioUnitario(detalleVenta.getPrecioUnitario())
                .subtotal(detalleVenta.getSubtotal())
                .build();
    }
}
