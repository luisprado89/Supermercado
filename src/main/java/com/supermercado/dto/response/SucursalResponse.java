package com.supermercado.dto.response;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Builder
public class SucursalResponse {
    private Long id; // El identificador único de la sucursal en la base de datos.
    private String nombre; // El nombre de la sucursal
    private String direccion; // La dirección fisica
    // El estado del registro para nuestro borrado lógico
    // true = La sucursal sigue abierta/visible en el sistema.
    // false = La sucursal fue 'eliminada' (cerrada) por un administrador.
    private Boolean activo;

}
