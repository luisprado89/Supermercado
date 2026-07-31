package com.supermercado.dto.request;

import com.supermercado.enums.CategoriaProducto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductoRequest {

    // =====================================================================
    // ATRIBUTOS Y VALIDACIONES
    // =====================================================================

    /**
     * Diferencia entre @NotNull y @NotBlank
     * - @NotNull: No permite 'null' (nulo), Pero permite un string vacío "".
     * - @NotBlank: No permite 'null' y tampoco permite un string vacío "" ni solo espacios "  ".
     * VALIDACIÓN @Size: limita la cantidad de caracteres, si en Postman envía un nombre de 101 letras,
     * Spring lo rechaza aquí antes de llegar a la BD, ya que es VARCHAR(100) en MySQL.
     * VALIDACIÓN @Positive: Garantiza que el número sea MAYOR estrictamente a 0 (no permite 0 ni negativos).
     * Es más semántico (tiene más sentido leído) que usar @Min(value=1) para precios.
     */

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private BigDecimal precio;
    @NotNull(message = "La categoria es obligatoria")
    private CategoriaProducto categoria;
}
