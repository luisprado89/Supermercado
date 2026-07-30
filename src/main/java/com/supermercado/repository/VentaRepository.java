package com.supermercado.repository;

import com.supermercado.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository // Le dice a Spring: 'Esta interfaz hablará con la BD, si algo falla aquí, se convierte en un error de BD MySQL'
public interface VentaRepository extends JpaRepository<Venta, Long> {
    /**
     * Repositorio de acceso a datos para la entidad de dominio.
     *
     * <p>Al extender de JpaRepository, esta interfaz hereda automáticamente una API completa
     * para la persistencia y gestión de datos, incluyendo operaciones CRUD esenciales y
     * capacidades de paginación/ordenamiento sin requerir implementación manual:</p>
     *
     * <ul>
     *   <li>{@code save(entity)}: Persiste una nueva entidad o actualiza una existente en la base de datos.</li>
     *   <li>{@code findById(id)}: Recupera un registro específico utilizando su clave primaria(SELECT * WHERE id=?).</li>
     *   <li>{@code findAll()}: Tra todos los registros.</li>
     *   <li>{@code deleteById(id)}: Elimina de forma física el registro asociado al ID proporcionado.</li>
     *   <li>{@code count()}: Devuelve el número total de registros existentes en la tabla.</li>
     * </ul>
     *
     * <p><b>Consultas Personalizadas:</b></p>
     * <p>La anotación {@code @Query} se utiliza para definir consultas avanzadas mediante JPQL
     * (Java Persistence Query Language) o SQL nativo cuando los métodos derivados o las
     * convenciones de nombres de Spring Data JPA no resultan suficientes para cubrir la lógica
     * de negocio requerida.</p>
     *
     *   Retorna un {@link Optional} para prevenir excepciones de tipo {@link NullPointerException}
     *   en caso de que la entidad no exista.
     *
     *  <p>Obliga a verificar la presencia del objeto o manejar su ausencia con algún mensaje
     *  (por ejemplo, con {@code .orElseThrow()}) antes de acceder a sus métodos.</p>
     *
     */
    // SQL Generado: SELECT * FROM venta WHERE id = ? AND activo = true;
    Optional<Venta> findByIdAndActivoTrue(Long id);

    //Cuando el filtro tiene más de 2 o 3 condiciones, la buena práctica es usar @Query porque se lee mucho mejor.
    @Query("SELECT v FROM Venta v " +
            "WHERE v.sucursal.id = :sucursalId " +
            "AND v.fecha = :fecha " +
            "AND v.activo = true " +
            "ORDER BY v.id DESC"
    )
    //Los parámetros que recibe el método. Fíjate en las anotaciones @Param.
    List<Venta> findBySucursalIdAndFechaAndActivoTrue(
            @Param("sucursalId") Long sucursalId,
            @Param("fecha") LocalDate fecha
    );

}
