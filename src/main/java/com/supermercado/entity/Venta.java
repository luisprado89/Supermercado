package com.supermercado.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity // Indica a JPA que esta es una entidad y se mapeará en una tabla de la base de datos.
@Table(name = "venta")  // Especifica el nombre exacto de la tabla ('venta') en la BD de MySQL.
@Getter // (Lombok) Genera automáticamente los metodos getx() para todos los atributos de la clase.
@Setter // (Lombok) Genera automáticamente los metodos setx() para todos los atributos de la clase.
@NoArgsConstructor // (Lombok) Genera un constructor vacío (sin parámetros), requerido obligatoriamente por JPA/Hibernate.
@AllArgsConstructor// (Lombok) Genera un constructor que recibe todos los atributos de la clase como parámetros.
@Builder // (Lombok) Implementa el patrón de diseño Builder para construir objetos de forma fluida (ej: Venta.builder().nombre("..").build()).
public class Venta {
    @Id //Clave primaria autoincremental ideantado.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha; // LocalDate: En MySQL esto se crea como tipo de dato DATE (Año-Mes-Día).
    @Column(name = "total", nullable = false, precision = 12, scale = 2)
    private BigDecimal total; // El total de la boleta. DECIMAL(12,2) en MySQL.
    @Column(name = "activo", nullable = false)
    @Builder.Default
    private Boolean activo = true;

    // Relación con SUCURSAL este es (El lado JEFE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sucursal_id", nullable = false) // Creará la columna 'sucursal_id' en la tabla 'Venta' en MySQL
    private Sucursal sucursal;

    @OneToMany(
            mappedBy = "venta", // Le decimos que busque el atributo venta
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch =  FetchType.LAZY
    )
    @Builder.Default // Inicializamos la lista vacía por defecto para evitar el NulPointerExceptions.
    private List<DetalleVenta> detalles = new ArrayList<>();

}
