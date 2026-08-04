package com.supermercado.service;

import com.supermercado.dto.request.VentaRequest;
import com.supermercado.dto.response.VentaResponse;

import java.time.LocalDate;
import java.util.List;

/**
 * Define las operaciones de negocio relacionadas con la gestión de ventas.
 *
 * <p>Esta interfaz representa el contrato que debe implementar cualquier
 * servicio encargado de registrar, consultar y anular ventas dentro de
 * la aplicación.</p>
 *
 * <p>Las operaciones disponibles respetan las reglas de negocio del sistema.
 * Por este motivo, no se incluye un método de actualización, ya que una venta
 * registrada no puede modificarse.</p>
 */
public interface VentaService {
    /**
     * Registra una nueva venta.
     *
     * @param ventaRequest información necesaria para registrar la venta.
     * @return venta registrada.
     */
    VentaResponse create(VentaRequest ventaRequest);
    /**
     * Obtiene las ventas registradas en una sucursal para una fecha determinada.
     *
     * @param sucursalId identificador de la sucursal.
     * @param fecha fecha de consulta.
     * @return lista de ventas que cumplen los criterios de búsqueda.
     */
    List<VentaResponse> findBySucursalAndDate(Long sucursalId, LocalDate fecha);
    /**
     * Anula una venta existente.
     *
     * @param id identificador de la venta.
     */
    void cancel(Long id);
    /**
     * Obtiene una venta a partir de su identificador.
     *
     * @param id identificador de la venta.
     * @return venta encontrada.
     */
    VentaResponse findById(Long id);
}
