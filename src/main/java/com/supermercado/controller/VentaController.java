package com.supermercado.controller;

import com.supermercado.dto.request.VentaRequest;
import com.supermercado.dto.response.VentaResponse;
import com.supermercado.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador REST encargado de exponer los endpoints relacionados
 * con la gestión de ventas.
 *
 * <p>Recibe las solicitudes HTTP, válida los datos de entrada y delega
 * la lógica de negocio en la capa de servicios.</p>
 */
@Tag(
        name = "Ventas",
        description = "API para la gestión de ventas del supermercado"
)
@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VentaController {
    /**
     * Servicio encargado de gestionar las operaciones de negocio
     * relacionadas con las ventas.
     */
    private final VentaService ventaService;
    /**
     * Registra una nueva venta.
     *
     * @param ventaRequest información necesaria para registrar la venta.
     * @return venta creada junto con el código HTTP 201 (Created).
     */
    @Operation(summary = "Registrar una nueva venta")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Venta registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o regla de negocio incumplida"),
            @ApiResponse(responseCode = "404", description = "Sucursal o producto no encontrado")
    })
    @PostMapping
    public ResponseEntity<VentaResponse> create(
            @Valid @RequestBody VentaRequest ventaRequest) {
        VentaResponse response = ventaService.create(ventaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    /**
     * Obtiene las ventas registradas en una sucursal para una fecha determinada.
     *
     * @param sucursalId identificador de la sucursal.
     * @param fecha fecha de consulta con formato {@code yyyy-MM-dd}.
     * @return lista de ventas encontradas.
     */
    @Operation(summary = "Buscar ventas por sucursal y fecha")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    @GetMapping
    public ResponseEntity<List<VentaResponse>> findByBranchAndDate(
            @RequestParam Long sucursalId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha) {

        return ResponseEntity.ok(ventaService.findBySucursalAndDate(sucursalId, fecha));
    }
    /**
     * Realiza la anulación de una venta.
     *
     * <p>La operación corresponde a un borrado lógico, por lo que la venta
     * permanece almacenada en la base de datos.</p>
     *
     * @param id identificador de la venta.
     * @return respuesta sin contenido (HTTP 204).
     */
    @Operation(summary = "Anular una venta")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Venta anulada correctamente"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {

        ventaService.cancel(id);

        return ResponseEntity.noContent().build();
    }
    /**
     * Obtiene una venta mediante su identificador.
     *
     * @param id identificador de la venta.
     * @return venta encontrada.
     */
    @Operation(summary = "Buscar una venta por su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venta encontrada"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse> findById(@PathVariable Long id) {

        return ResponseEntity.ok(ventaService.findById(id));
    }
}
