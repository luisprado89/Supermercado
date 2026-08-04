package com.supermercado.service.impl;

import com.supermercado.dto.request.SucursalRequest;
import com.supermercado.dto.response.SucursalResponse;
import com.supermercado.entity.Sucursal;
import com.supermercado.exception.ResourceNotFoundException;
import com.supermercado.mapper.SucursalMapper;
import com.supermercado.repository.SucursalRepository;
import com.supermercado.service.SucursalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio encargado de gestionar las operaciones relacionadas con las sucursales.
 *
 * <p>Esta clase contiene toda la lógica de negocio necesaria para crear, actualizar, consultar y realizar
 * el borrado lógico de las sucursales, coordinando la interacción entre el repositorio y el mapper. </p>
 *
 * <p>Las operaciones de la escritura se ejecutan dentro de transacciones para garantizar la integridad
 * de los datos.</p>
 */
@Service
@RequiredArgsConstructor
public class SucursalServiceImpl implements SucursalService {
    /**
     * Dependencias necesarias para acceder a la persistencia y transformas entidades en objetos de respuesta.
     */
    private final SucursalRepository sucursalRepository;
    private final SucursalMapper sucursalMapper;

    /**
     * Obtiene todas las sucursales activas.
     *
     * @return lista de sucursales.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SucursalResponse> findAll() {
        return sucursalRepository.findByActivoTrueOrderByNombreAsc().stream()
                .map(sucursalMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Crea una nueva sucursal.
     *
     * @param sucursalRequest datos necesarios para crear la sucursal.
     * @return sucursal creada.
     */
    @Override
    @Transactional
    public SucursalResponse create(SucursalRequest sucursalRequest) {
        Sucursal sucursal = sucursalMapper.toEntity(sucursalRequest);
        Sucursal sucursalSaved = sucursalRepository.save(sucursal);
        return  sucursalMapper.toResponse(sucursalSaved);
    }

    /**
     * Actualiza la informaci´çon de una sucursal existente.
     *
     * @param id identificador de la sucursal.
     * @param sucursalRequest datos actualizados de la sucursal.
     * @return sucursal actualizada.
     * @throws ResourceNotFoundException si la sucursal no existe o está inactivo.
     */
    public SucursalResponse update(Long id, SucursalRequest sucursalRequest) {
        Sucursal sucursal = sucursalRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Sucursal no encontrada con ID: "+id));
        sucursal.setNombre(sucursalRequest.getNombre());
        sucursal.setDireccion( sucursalRequest.getDireccion());

        Sucursal sucursalUpdated = sucursalRepository.save(sucursal);
        return  sucursalMapper.toResponse(sucursalUpdated);
    }

    /**
     * Realiza el borrado lógico de una sucursal.
     *
     * <p>La sucursal permanece almacenada en la base de datos, pero deja de estar disponible para las consultas
     * habituales al marcarse como inactiva.</p>
     *
     * @param id identificador de la sucursal.
     * @throws ResourceNotFoundException si la sucursal no existe o está inactivo.
     */
    @Override
    @Transactional
    public void delete(Long id){
        Sucursal sucursal = sucursalRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Sucursal no encontrada con ID: "+id));
        sucursal.setActivo(false); // Borrado lógico de la sucursal.
        sucursalRepository.save(sucursal);
    }

    /**
     * Obtiene una sucursal activa mediante su identificador.
     * @param id identificador de la sucursal.
     * @return sucursal encontrada
     * @throws ResourceNotFoundException si la sucursal no existe o está inactiva.
     */
    @Override
    @Transactional(readOnly = true)
    public SucursalResponse findById(Long id){
        Sucursal sucursal = sucursalRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Sucursal no encontrada con ID: "+ id));
        return   sucursalMapper.toResponse(sucursal);
    }
}
