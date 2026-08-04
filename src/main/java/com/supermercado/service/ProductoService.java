package com.supermercado.service;

import com.supermercado.dto.request.ProductoRequest;
import com.supermercado.dto.response.ProductoResponse;

import java.util.List;

/**
 * Define las operaciones de negocio relacionadas con la gestión de productos.
 *
 * <p>Esta interfaz representa el contrato que debe implementar cualquier servicio encargado de crear, actualizar
 * consultar y eliminar productos dentro de la aplicación.</p>
 */
public interface ProductoService {
    /**
     * Obtiene los productos activos.
     *
     * @return lista de productos.
     */
    List<ProductoResponse> findAll();

    /**
     * Crea un nuevo producto.
     *
     * @param productoRequest datos necesarios para crear el producto.
     * @return producto creado.
     */
    ProductoResponse create(ProductoRequest productoRequest);

    /**
     * Actualiza la información de un producto existente.
     * @param id identificador del producto.
     * @param productoRequest datos actualizados del producto.
     * @return producto actualizado.
     */
    ProductoResponse update(Long id, ProductoRequest productoRequest);

    /**
     * Realiza el borrado lógico de un producto.
     * @param id identificador del producto.
     */
    void  delete(Long id);

    /**
     * Obtiene un producto a partir de su identificador.
     * @param id identificador del producto.
     * @return producto encontrado.
     */
    ProductoResponse findById(Long id);
}
