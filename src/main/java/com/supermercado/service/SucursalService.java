package com.supermercado.service;

import com.supermercado.dto.request.SucursalRequest;
import com.supermercado.dto.response.SucursalResponse;

import java.util.List;

/**
 * Define las operaciones de negocio relacionadas con la gestión de sucursales.
 *
 * <p>Esta interfaz representa el contrato que debe implementar cualquier
 * servicio encargado de crear, actualizar, consultar y eliminar sucursales
 * dentro de la aplicación.</p>
 */
public interface SucursalService {
    /**
     * Obtiene todas las sucursales activas.
     *
     * @return lista de sucursales.
     */
    List<SucursalResponse> findAll();

    /**
     * Crea una nueva sucursal.
     *
     * @param sucursalRequest datos necesarios para crear la sucursal.
     * @return sucursal creada.
     */
    SucursalResponse create(SucursalRequest sucursalRequest);

    /**
     * Actualiza la información de una sucursal existente.
     *
     * @param id identificador de la sucursal.
     * @param sucursalRequest datos actualizados de la sucursal.
     * @return sucursal actualizada.
     */
    SucursalResponse update(Long id, SucursalRequest sucursalRequest);

    /**
     * Realiza el borrado lógico de una sucursal.
     *
     * @param id identificador de la sucursal.
     */
    void delete(Long id);

    /**
     * Obtiene una sucursal a partir de su identificador.
     *
     * @param id identificador de la sucursal.
     * @return sucursal encontrada.
     */
    SucursalResponse findById(Long id);
}
