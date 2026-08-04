package com.supermercado.controller;

import com.supermercado.dto.response.ProductoMasVendidoResponse;
import com.supermercado.service.EstadisticaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Expone los endpoints REST relacionados con las estadísticas de ventas.
 *
 * <p>Este controlador delega toda la lógica de negocio en
 * {@link EstadisticaService}, proporcionando operaciones para consultar
 * el producto más vendido y obtener el ranking de productos con mayor
 * volumen de ventas.</p>
 */
@RestController
@RequestMapping("/api/estadisticas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EstadisticaController {

    private final EstadisticaService estadisticaService;

    /**
     * Obtiene el producto con mayor cantidad de unidades vendidas.
     *
     * @return información del producto más vendido.
     */
    @GetMapping("/producto-mas-vendido")
    public ResponseEntity<ProductoMasVendidoResponse> findBestSellingProducto() {
        return ResponseEntity.ok(estadisticaService.findBestSellingProducto());
    }

    /**
     * Obtiene el ranking de los productos más vendidos.
     *
     * <p>Si no se especifica la cantidad, se devuelven los cinco
     * productos con mayor volumen de ventas.</p>
     *
     * @param quantity número máximo de productos a devolver.
     * @return lista ordenada de productos más vendidos.
     */
    @GetMapping("/top-productos")
    public ResponseEntity<List<ProductoMasVendidoResponse>> findTopSellingProductos(
            @RequestParam(defaultValue = "5") int quantity) {

        return ResponseEntity.ok(estadisticaService.findTopSellingProductos(quantity));
    }
}
