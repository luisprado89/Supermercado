package com.supermercado.service.impl;

import com.supermercado.dto.request.DetalleVentaRequest;
import com.supermercado.dto.request.VentaRequest;
import com.supermercado.dto.response.VentaResponse;
import com.supermercado.entity.DetalleVenta;
import com.supermercado.entity.Producto;
import com.supermercado.entity.Sucursal;
import com.supermercado.entity.Venta;
import com.supermercado.exception.ResourceNotFoundException;
import com.supermercado.mapper.VentaMapper;
import com.supermercado.repository.ProductoRepository;
import com.supermercado.repository.SucursalRepository;
import com.supermercado.repository.VentaRepository;
import com.supermercado.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio encargado de gestionar las operaciones
 * relacionadas con las ventas.
 *
 * <p>Esta clase contiene la lógica de negocio necesaria para registrar,
 * consultar y anular ventas, coordinando la interacción entre los
 * repositorios y los mappers.</p>
 *
 * <p>Las operaciones de escritura se ejecutan dentro de transacciones para
 * garantizar la integridad de los datos y evitar estados inconsistentes
 * ante posibles errores durante el proceso.</p>
 */
@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements VentaService {
    /** Dependencias necesarias para acceder a la persistencia y transformar entidades en objetos de respuesta.
    **/
    private final VentaRepository ventaRepository;
    private final SucursalRepository sucursalRepository;
    private final ProductoRepository productoRepository;
    private final VentaMapper ventaMapper;

    /**
     * Registra una nueva venta.
     *
     * <p>La operación valida la existencia de la sucursal, construye la venta y
     * sus detalles, calcula el importe total y persiste toda la información en
     * una única transacción.</p>
     *
     * @param ventaRequest datos necesarios para crear/registrar la venta.
     * @return venta creada/registrada.
     * @throws ResourceNotFoundException si la sucursal no existe o está inactivo.
     */
    @Override
    @Transactional
    public VentaResponse create(VentaRequest ventaRequest) {
        Sucursal sucursal = sucursalRepository.findByIdAndActivoTrue(ventaRequest.getSucursalId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Sucursal no encontrada con ID: "+ventaRequest.getSucursalId()));
        Venta venta = Venta.builder()
                .fecha(LocalDate.now())
                .sucursal(sucursal)
                .total(BigDecimal.ZERO)
                .activo(true)
                .detalles(new ArrayList<>())
                .build();
        BigDecimal totalVenta = ventaRequest.getDetalle().stream()
                .map(detalleVentaRequest -> createDetalleVenta(venta, detalleVentaRequest))
                .map(DetalleVenta::getSubtotal)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        venta.setTotal(totalVenta);
        Venta ventaSaved = ventaRepository.save(venta);
        return ventaMapper.toResponse(ventaSaved);

    }

    private DetalleVenta createDetalleVenta(Venta venta, DetalleVentaRequest detalleVentaRequest) {
        Producto producto = productoRepository.findByIdAndActivoTrue(detalleVentaRequest.getProductoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto no encontrado conID: "+detalleVentaRequest.getProductoId()));
        BigDecimal subtotal = producto.getPrecio()
                .multiply(BigDecimal.valueOf(detalleVentaRequest.getCantidad()));
        DetalleVenta detalleVenta = DetalleVenta.builder()
                .venta(venta)
                .producto(producto)
                .cantidad(detalleVentaRequest.getCantidad())
                .precioUnitario(producto.getPrecio())
                .subtotal(subtotal)
                .build();
        venta.getDetalles().add(detalleVenta);
        return  detalleVenta;
    }

    /**
     * Obtiene las ventas registradas en una sucursal para una fecha determinada.
     *
     * @param sucursalId identificador de la sucursal.
     * @param fecha fecha de consulta.
     * @return lista de ventas encontradas.
     * @throws ResourceNotFoundException si la sucursal no existe o está inactivo.
     */
    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> findBySucursalAndDate(Long sucursalId, LocalDate fecha) {
        if (!sucursalRepository.existsByIdAndActivoTrue(sucursalId)){
            throw  new ResourceNotFoundException("Sucursal no encontrado con ID: "+sucursalId);
        }
        return ventaRepository.findBySucursalIdAndFechaAndActivoTrue(sucursalId,fecha).stream()
                .map(ventaMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Realiza el borrado lógico de una venta.
     *
     * <p>La venta permanece almacenada en la base de datos, pero deja de estar
     * disponible para las consultas habituales al marcarse como inactiva.</p>
     *
     * @param id identificador de la venta.
     * @throws ResourceNotFoundException si la venta no existe o está inactivo.
     */
    @Override
    @Transactional
    public void cancel(Long id) {
        Venta venta = ventaRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: "+id));
        venta.setActivo(false);
        ventaRepository.save(venta);
    }

    /**
     * Obtiene una venta activa mediante su identificador.
     *
     * @param id identificador de la venta.
     * @return venta encontrada.
     * @throws ResourceNotFoundException si la venta no existe o está inactiva.
     */
    @Override
    @Transactional(readOnly = true)
    public VentaResponse findById(Long id) {
        Venta venta = ventaRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: "+id));
        return  ventaMapper.toResponse(venta);
    }
}
