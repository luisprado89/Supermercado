package com.supermercado.controller;

import com.supermercado.dto.request.SucursalRequest;
import com.supermercado.dto.response.SucursalResponse;
import com.supermercado.service.SucursalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST encargado de exponer los endpoints relacionados
 * con la gestión de sucursales.
 *
 * <p>Recibe las solicitudes HTTP, valida los datos de entrada y delega
 * la lógica de negocio en la capa de servicios.</p>
 */
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
    @GetMapping("/{id}")
    public ResponseEntity<SucursalResponse> findById(@PathVariable Long id) {

        return ResponseEntity.ok(sucursalService.findById(id));
    }

}
