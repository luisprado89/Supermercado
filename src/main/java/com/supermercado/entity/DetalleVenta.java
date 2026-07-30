package com.supermercado.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_venta")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class DetalleVenta {
    @Id // Clave primaria (PRIMARY KEY) AUTO_INCREMENT en MySQL
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
    @Column(name = "precio_unitario",nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;
    @Column(name = "subtotal", nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    //  RELACIÓN: @ManyToOne (Muchos a Uno), Muchos DetalleVenta pueden pertenecer a una sola Venta.
    @ManyToOne(fetch = FetchType.LAZY) // LAZY significa "Perezoso", es una tecnica de rendimiento crítico, solo tra la id de la venta.
    @JoinColumn(name = "venta_id", nullable = false) // Asi se llamara la columna en MySQL, NOT NULL.
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY) // Muchos DetalleVenta pueden referirse al MISMO producto.
    @JoinColumn(name = "producto_id",nullable = false) // Crea otra llave FORÁNEA en la tabla que apunta a la tabla productos.
    private Producto producto;

}
