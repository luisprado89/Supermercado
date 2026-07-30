package com.supermercado.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="sucursal" )
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class Sucursal {
    @Id // Clave primaria autoincremental
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nombre; // NOT NULL, VARCHAR(100) en MySQL.
    @Column(nullable = false, length = 200)
    private String direccion; // NOT NULL, VARCHAR(200) en MySQL
    @Column(name = "activo", nullable = false)
    @Builder.Default // Booleano por defecto true. Recordamos que @Builder.DEfault es VITAL aquí.
    private Boolean activo = true;
    /**
     * Relación Uno a Muchosm una sucural puede tener muchas Ventas asociadas
     */
    @OneToMany(
            mappedBy = "sucursal", // le indica que en la clase Venta hay un atributo 'sucursal', que existe físicamente en la TABLA VENTA en la columna 'sucursal_id'
            cascade = CascadeType.ALL, // Arrastra las operaciones
            fetch =  FetchType.LAZY
    )
    @Builder.Default // Asegura que el builder de Lombok respete ese valor por defecto
    private List<Venta> ventas = new ArrayList<>();

}
