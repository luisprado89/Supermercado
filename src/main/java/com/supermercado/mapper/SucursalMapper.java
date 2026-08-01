package com.supermercado.mapper;

import com.supermercado.dto.request.SucursalRequest;
import com.supermercado.dto.response.SucursalResponse;
import com.supermercado.entity.Sucursal;
import org.springframework.stereotype.Component;


/**
 * Mapper encargado de convertir entre la entidad {@link Sucursal}  y los DTOs utilizados por la API.
 *
 * <p>Centraliza la transformación de datos entre la capa de presentación
 * y el modelo de persistencia, evitando duplicar esta lógica en los servicios.</p>
 */
@Component
public class SucursalMapper {
    /**
     * Convierte un {@link SucursalRequest} en una entidad {@link Sucursal}.
     *
     * <p>Durante la creación de la entidad se inicializan los atributos que no
     * son proporcionados por el cliente, como el estado activo.</p>
     *
     * @param sucursalRequest datos recibidos en la petición.
     * @return entidad lista para ser persistida.
     */
    public Sucursal toEntity(SucursalRequest sucursalRequest){
        return Sucursal.builder()
                .nombre(sucursalRequest.getNombre())
                .direccion(sucursalRequest.getDireccion())
                .activo(true) // Las nuevas sucursales se crean activas por defecto.
                .build(); // .build() termina de armar el objeto y lo devuelve
    }

    /**
     * Convierte una entidad {@link Sucursal} en un {@link SucursalResponse}.
     *
     * @param sucursal entidad obtenida de la base de datos.
     * @return DTO con la información que será enviada al cliente.
     */
    public SucursalResponse toResponse(Sucursal sucursal) {
        return SucursalResponse.builder()
                .id(sucursal.getId())
                .nombre(sucursal.getNombre())
                .direccion(sucursal.getDireccion())
                .activo(sucursal.getActivo())
                .build(); // .build() termina de armar el objeto y lo devuelve
    }
}
