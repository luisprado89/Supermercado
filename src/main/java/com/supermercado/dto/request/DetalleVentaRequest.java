package com.supermercado.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class DetalleVentaRequest {
    /**
     * No usamos datos de tipo primitivo como 'long' o 'int' que nunca pueden ser nulo (si no tiene valor, vale 0).
     * 'Long', 'Integer' son objetos (Wrapper) que SÍ puede ser nulo.
     * Si usáramos 'long', @NotNull no tendría sentido porque nunca llegaría nulo llegaría 0.
     * Como usamos 'Long', 'Integer', si Postman no envía el campo, llega 'null' y la validación salta correctamente.
     */
    // Esta regla de validación dice que este campo no puede ser nulo (null)
    @NotNull(message = "El ID del producto es obligatorio")
    @Positive(message = "El ID del producto debe ser positivo")
    private Long productoId;

    @NotNull(message = "La cantidad es obligatoria")
    // Regla de validación que dice, este número NO puede ser menor a 1, esto evita que usuarios maliciosos envie cantidades negativas, ej: cantidad: -5 o cero
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;
}
