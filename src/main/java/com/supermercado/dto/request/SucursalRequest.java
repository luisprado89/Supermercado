package com.supermercado.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class SucursalRequest {
    // @NotBlank No admite nulo, no admite vacío "", no admite solo espacios "  ".
    @NotBlank(message = "El nombre de la sucursal es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 200, message = " La dirección no puede exceder 200 caracteres")
    private String direccion;
}
