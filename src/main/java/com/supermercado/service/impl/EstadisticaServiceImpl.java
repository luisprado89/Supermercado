package com.supermercado.service.impl;

import com.supermercado.dto.response.ProductoMasVendidoResponse;
import com.supermercado.entity.DetalleVenta;
import com.supermercado.exception.BusinessRuleException;
import com.supermercado.repository.DetalleVentaRepository;
import com.supermercado.service.EstadisticaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio encargado de generar estadísticas
 * relacionadas con las ventas del sistema.
 *
 * <p>Esta clase procesa la información almacenada en los detalles de venta
 * para calcular indicadores como el producto más vendido y el ranking de
 * productos con mayor volumen de ventas.</p>
 *
 * <p>Todas las operaciones son de solo lectura, por lo que las transacciones
 * se ejecutan en modo {@code readOnly} para optimizar el acceso a la base
 * de datos.</p>
 */
@Service
@RequiredArgsConstructor
public class EstadisticaServiceImpl implements EstadisticaService {
    // Repositorio utilizado para acceder a los detalles de venta.
    private final DetalleVentaRepository detalleVentaRepository;
    /**
     * Obtiene el producto con mayor cantidad de unidades vendidas.
     *
     * <p>La información se calcula agrupando los detalles de venta por producto,
     * sumando las cantidades vendidas y seleccionando el registro con mayor
     * volumen de ventas.</p>
     *
     * @return producto más vendido.
     * @throws BusinessRuleException si no existen ventas registradas.
     */

    @Override
    @Transactional(readOnly = true)
    public ProductoMasVendidoResponse findBestSellingProducto(){
        List<DetalleVenta> detalleVentas = detalleVentaRepository.findAllByVentaActiva();
        if (detalleVentas.isEmpty()){
            throw  new BusinessRuleException("No hay ventas registradas.");
        }
        return detalleVentas.stream()
                .collect(Collectors.groupingBy(
                        DetalleVenta::getProducto,
                        Collectors.summarizingInt(DetalleVenta::getCantidad)
                ))
                .entrySet().stream()
                .map( entry -> ProductoMasVendidoResponse.builder()
                        .productoId(entry.getKey().getId())
                        .nombreProducto(entry.getKey().getNombre())
                        .totalCantidadVendida(entry.getValue().getSum())
                        .totalIngresos(calcularIngresosPorProductos(detalleVentas,entry.getKey().getId()))
                        .build())
                .max(Comparator.comparingLong(ProductoMasVendidoResponse::getTotalCantidadVendida))
                .orElseThrow(() -> new IllegalStateException(
                        "Error interno al calcular el producto más vendido."));
    }
    /**
     * Obtiene el ranking de los productos más vendidos.
     *
     * <p>Los resultados se ordenan de forma descendente según la cantidad de
     * unidades vendidas y se limita el número de elementos devueltos.</p>
     *
     * @param quantity número máximo de productos a recuperar.
     * @return lista de productos más vendidos.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductoMasVendidoResponse> findTopSellingProductos(int quantity){
        List<DetalleVenta> detalleVentas = detalleVentaRepository.findAllByVentaActiva();
        if (detalleVentas.isEmpty()){
            return List.of();// Lista vacía e inmutable
        }
        return detalleVentas.stream()
                .collect(Collectors.groupingBy(
                        DetalleVenta::getProducto,
                        Collectors.summarizingInt(DetalleVenta::getCantidad)
                ))
                .entrySet().stream()
                .map( entry -> ProductoMasVendidoResponse.builder()
                        .productoId(entry.getKey().getId())
                        .nombreProducto(entry.getKey().getNombre())
                        .totalCantidadVendida(entry.getValue().getSum())
                        .totalIngresos(calcularIngresosPorProductos(detalleVentas,entry.getKey().getId()))
                        .build())
                // Agrupa los productos por cantidad vendida. // Ordena los resultados de mayor a menor cantidad vendida.
                .sorted(Comparator.comparingLong(ProductoMasVendidoResponse::getTotalCantidadVendida).reversed())
                .limit(quantity) // Limita el número de resultados devueltos.
                .collect(Collectors.toList());
    }
    /**
     * Calcula los ingresos generados por un producto.
     *
     * <p>El cálculo se realiza sumando los subtotales de todos los detalles de
     * venta asociados al producto indicado.</p>
     *
     * @param detalleVentas lista de detalles de venta.
     * @param productoId identificador del producto.
     * @return importe total generado por el producto.
     */
    private BigDecimal calcularIngresosPorProductos(List<DetalleVenta> detalleVentas, Long productoId){
        return detalleVentas.stream()
                .filter(d -> d.getProducto().getId().equals(productoId))
                .map(DetalleVenta::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
