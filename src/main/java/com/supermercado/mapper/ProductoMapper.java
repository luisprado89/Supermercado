package com.supermercado.mapper;

import com.supermercado.dto.request.ProductoRequest;
import com.supermercado.dto.response.ProductoResponse;
import com.supermercado.entity.Producto;
import org.springframework.stereotype.Component;

/**
 * Mapper encargado de convertir entre la entidad {@link Producto}
 * y los DTOs utilizados por la API.
 *
 * <p>Centraliza la transformación de datos entre la capa de presentación
 * y la capa de persistencia, manteniendo esta responsabilidad fuera de
 * los servicios.</p>
 */
@Component
public class ProductoMapper {
    /**
     * Convierte un {@link ProductoRequest} en una entidad {@link Producto}.
     *
     * <p>Durante la creación de la entidad se inicializan los atributos
     * que no son proporcionados por el cliente, como el estado activo.</p>
     *
     * @param productoRequest datos recibidos en la petición.
     * @return entidad lista para ser persistida.
     */
    public Producto toEntity(ProductoRequest productoRequest){
        return Producto.builder()
                .nombre(productoRequest.getNombre())
                .precio(productoRequest.getPrecio())
                .categoria(productoRequest.getCategoria())
                .activo(true) // Los nuevos productos se crean activos por defecto.
                .build(); // .build() termina de armar el objeto y lo devuelve
    }

    /**
     * Convierte una entidad {@link Producto} en un
     * {@link ProductoResponse}.
     *
     * @param producto entidad obtenida de la base de datos.
     * @return DTO con la información que será enviada al cliente.
     */
    public ProductoResponse toResponse(Producto producto) {
        return ProductoResponse.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .precio(producto.getPrecio())
                .categoria(producto.getCategoria())
                .activo(producto.getActivo())
                .build();
    }
}
