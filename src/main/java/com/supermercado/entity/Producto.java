package com.supermercado.entity;

import com.supermercado.enums.CategoriaProducto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "producto")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class Producto {
    /**
     * Por qué usamos BigDecimal y NO DOUBLE
     * En Java, si haces: 0.1 + 0.2 con DOUBLE, el resultado es 0.30000000000000004.
     * Esto en un sistema financiero las cuentas no cuadrarían.
     * BigDecimal hace cálculos EXACTOS. Además, en la BD se guarda como DECIMAL(10,2).
     * La anotación @Column(precision=10,scale=2) de la entidad exige BigDecimal.
     */
    @Id // Clave primaria autoincremental.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nombre; // NOT NULL. VARCHAR(100) en MySQL.
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio; // EL dinero DECIMAL(10.2)
    @Enumerated(EnumType.STRING) // Guarda el texto literalmente en MySQL -> "ALIMENTOS",..
    @Column(nullable = false, length = 50)
    private CategoriaProducto categoria;
    @Column(name = "activo", nullable = false)
    @Builder.Default // Cuando no especifican el campo activo, introduce el valor por defecto que está al lado (true)
    private Boolean activo = true;
}
