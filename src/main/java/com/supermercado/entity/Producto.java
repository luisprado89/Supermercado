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
