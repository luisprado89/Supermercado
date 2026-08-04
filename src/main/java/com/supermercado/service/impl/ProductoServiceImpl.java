package com.supermercado.service.impl;

import com.supermercado.dto.request.ProductoRequest;
import com.supermercado.dto.response.ProductoResponse;
import com.supermercado.entity.Producto;
import com.supermercado.exception.ResourceNotFoundException;
import com.supermercado.mapper.ProductoMapper;
import com.supermercado.repository.ProductoRepository;
import com.supermercado.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio encargado de gestionar todas ls operaciones relacionadas con los productos.
 * <p>Esta clase contiene la lógica de negocio necesaria para crear, actualizar, consultar y realizar borrado lógico
 * de los productos, coordinando la interacción entre el repositorio y el mapper.</p>
 * <p>Las operaciones de escritura se ejecutan dentro de transacciones para garantizar la integridad de los datos.</p>
 */
@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {
    // Dependencias necesarias para acceder a la persistencia y trandormar entidades en objetos respuesta.
    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    /**
     * Obtiene todos los productos activos.
     * @return lista de productos.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> findAll() {
        return productoRepository.findByActivoTrueOrderByNombreAsc().stream()
                .map(productoMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Crea un nuevo producto.
     * @param productoRequest datos necesarios para crear el producto.
     * @return producto creado.
     */
    @Override
    @Transactional
    public ProductoResponse create(ProductoRequest productoRequest) {
        Producto producto = productoMapper.toEntity(productoRequest);
        Producto productoSaved = productoRepository.save(producto);
        return  productoMapper.toResponse(productoSaved);
    }

    /**
     * Actualiza la información de un producto existente.
     * @param id identificador del producto.
     * @param productoRequest datos actualizados del producto.
     * @return producto actualizado.
     * @throws ResourceNotFoundException si el producto no existe o está inactivo.
     */
    @Override
    @Transactional
    public ProductoResponse update(Long id, ProductoRequest productoRequest) {
        // Recupera el producto activo o lanza una excepción si no existe.
        Producto producto = productoRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() ->new ResourceNotFoundException("Producto no encontrado con ID: "+id));
        producto.setNombre(productoRequest.getNombre());
        producto.setPrecio(productoRequest.getPrecio());
        producto.setCategoria(productoRequest.getCategoria());

        Producto productoUpdated = productoRepository.save(producto);
        return   productoMapper.toResponse(productoUpdated);

    }

    /**
     * Realiza el borrado lógico de un producto.
     * <p>El producto permanece almacenado en la base de datos, pero deja de estar disponible para las consultas
     * habituales al marcarse como inactivo, para no romper el historial de ventas.</p>
     * @param id identificador del producto.
     * @throws ResourceNotFoundException si el producto no existe o está inactivo.
     */
    @Override
    @Transactional
    public void delete(Long id){
        Producto producto = productoRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: "+id));
        producto.setActivo(false); // no aplicamos un Delete, si no más bien solo cambiamos su estado a 'false' para no romper el historial de ventas.
        productoRepository.save(producto);
    }

    /**
     * Obtiene un producto mediante su identificador.
     * @param id identificador del producto.
     * @return producto encontrado.
     * @throws ResourceNotFoundException si el producto no existe o está inactivo.
     */
    @Override
    @Transactional(readOnly = true)
    public ProductoResponse findById(Long id) {
        Producto producto = productoRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: "+id));
        return   productoMapper.toResponse(producto);
    }

}
