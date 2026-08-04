package com.supermercado.controller;

import com.supermercado.dto.request.SucursalRequest;
import com.supermercado.dto.response.SucursalResponse;
import com.supermercado.service.SucursalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

/**
 * Controlador REST encargado de exponer los endpoints relacionados
 * con la gestión de sucursales.
 *
 * <p>Recibe las solicitudes HTTP, valida los datos de entrada y delega
 * la lógica de negocio en la capa de servicios.</p>
 */
@Tag(
        name = "Sucursales",
        description = "API para la gestión de sucursales del supermercado"
)
@RestController
@RequestMapping("/api/sucursales")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SucursalController {
    /**
     * Servicio encargado de gestionar las operaciones de negocio relacionadas con las sucursales.
     */
    private final SucursalService sucursalService;

    /**
     * Obtiene todas las sucursales activas.
     *
     * @return lista de sucursales registradas.
     */
    @Operation(summary = "Obtener todas las sucursales")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucursales obtenidas correctamente")
    })
    @GetMapping
    public ResponseEntity<List<SucursalResponse>> findAll() {

        return ResponseEntity.ok(sucursalService.findAll());
    }
    /**
     * Registra una nueva sucursal.
     *
     * @param sucursalRequest información necesaria para crear la sucursal.
     * @return sucursal creada junto con el código HTTP 201 (Created).
     */
    @Operation(summary = "Registrar una nueva sucursal")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sucursal creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos")
    })
    @PostMapping
    public ResponseEntity<SucursalResponse> create(
            @Valid @RequestBody SucursalRequest sucursalRequest) {

        SucursalResponse response = sucursalService.create(sucursalRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza la información de una sucursal existente.
     *
     * @param id identificador de la sucursal.
     * @param sucursalRequest nuevos datos de la sucursal.
     * @return sucursal actualizada.
     */
    @Operation(summary = "Actualizar una sucursal")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucursal actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<SucursalResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody SucursalRequest sucursalRequest) {

        return ResponseEntity.ok(
                sucursalService.update(id, sucursalRequest));
    }
    /**
     * Realiza el borrado lógico de una sucursal.
     *
     * @param id identificador de la sucursal.
     * @return respuesta sin contenido (HTTP 204).
     */
    @Operation(summary = "Eliminar una sucursal")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Sucursal eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        sucursalService.delete(id);

        return ResponseEntity.noContent().build();
    }
    /**
     * Obtiene una sucursal mediante su identificador.
     *
     * @param id identificador de la sucursal.
     * @return sucursal encontrada.
     */
    @Operation(summary = "Buscar una sucursal por su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucursal encontrada"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SucursalResponse> findById(@PathVariable Long id) {

        return ResponseEntity.ok(sucursalService.findById(id));
    }

}
