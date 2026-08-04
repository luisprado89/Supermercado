package com.supermercado.service;

import com.supermercado.dto.response.ProductoMasVendidoResponse;

import java.util.List;

/**
 * Define las operaciones de negocio relacionadas con la obtención
 * de estadísticas del sistema.
 *
 * <p>Esta interfaz representa el contrato que debe implementar cualquier
 * servicio encargado de generar información estadística a partir de los
 * datos almacenados en la aplicación.</p>
 */
public interface EstadisticaService {
    /**
     * Obtiene el producto con mayor número de ventas.
     * @return información del producto más vendido.
     */
    ProductoMasVendidoResponse findBestSellingProducto();

    /**
     * Obtiene los productos más vendidos según la cantidad indicada.
     * @param quantity número máximo de productos a recuperar.
     * @return lista de los productos más vendidos.
     */
    List<ProductoMasVendidoResponse> findTopSellingProductos(int quantity);
}
