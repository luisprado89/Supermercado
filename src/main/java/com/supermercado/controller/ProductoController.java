package com.supermercado.controller;

import com.supermercado.dto.request.ProductoRequest;
import com.supermercado.dto.response.ProductoResponse;
import com.supermercado.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST encargado de gestionar las operaciones relacionadas
 * con los productos.
 *
 * <p>Expone los endpoints necesarios para crear, consultar, actualizar
 * y eliminar productos, delegando toda la lógica de negocio en
 * {@link ProductoService}.</p>
 */
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductoController {
    /**
     * Servicio encargado de gestionar las operaciones de negocio relacionadas con los productos.
     */
    private final ProductoService productoService;

    /**
     * Obtiene el listado de todos los productos activos.
     *
     * @return lista de productos.
     */
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> findAll() {
        return ResponseEntity.ok(productoService.findAll());
    }

    /**
     * Registra un nuevo producto.
     *
     * @param productoRequest datos del producto a registrar.
     * @return producto creado.
     */
    @PostMapping
    public ResponseEntity<ProductoResponse> create(
            @Valid @RequestBody ProductoRequest productoRequest) {

        ProductoResponse productoResponse = productoService.create(productoRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(productoResponse);
    }

    /**
     * Actualiza la información de un producto existente.
     *
     * @param id identificador del producto.
     * @param productoRequest nuevos datos del producto.
     * @return producto actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequest productoRequest) {

        return ResponseEntity.ok(productoService.update(id, productoRequest));
    }

    /**
     * Realiza el borrado lógico de un producto.
     *
     * @param id identificador del producto.
     * @return respuesta sin contenido.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene un producto mediante su identificador.
     *
     * @param id identificador del producto.
     * @return producto encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }
}