package com.supermercado.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class VentaRequest {
    // =====================================================================
    // ATRIBUTOS Y VALIDACIONES
    // =====================================================================

    @NotNull(message = "El ID de la sucursal es obligatorio") // El id de la sucursal no puede ser nulo
    @Positive(message = "El ID de la sucursal debe ser positivo")
    private Long sucursalId; // Si Postman envía 'sucursalId' null, se detiene aquí.

    @NotEmpty(message = "La venta debe tener al menos un producto") // Esto no puede ser nulo y tampoco puede estar vacío.
    @Valid // Validación en cascada, al ser una lista ejecuta todas sus valiaciones (@NotNull, @Min)
    private List<DetalleVentaRequest> detalle;
}
