package com.supermercado.repository;

import com.supermercado.entity.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {


    @Query("SELECT dv FROM DetalleVenta dv " +
            "JOIN dv.venta v " +
            "WHERE v.activo = true")
    List<DetalleVenta> findAllByVentaActiva();
}
