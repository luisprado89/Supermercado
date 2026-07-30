package com.supermercado.repository;

import com.supermercado.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // Le dice a Spring: 'Esta interfaz hablará con la BD, si algo falla aquí, se convierte en un error de BD Mysql'.
public interface ProductoRepository extends JpaRepository<Producto, Long> {
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
     *  <p>Obliga a verificar la presencia del objeto o manejar su ausencia con algún mensaje
     *  (por ejemplo, con {@code .orElseThrow()}) antes de acceder a sus métodos.</p>
     *
     */

    // SQL Generado: SELECT * FROM producto WHERE activo = true ORDER BY nombre ASC;
    List<Producto> findByActivoTrueOrderByNombreAsc();

    // SQL Generado: SELECT * FROM producto WHERE id = ? AND activo = true;
    Optional<Producto> findByIdAndActivoTrue(Long id);

    // SQL Generado: SELECT COUNT(*) > 0 FROM producto WHERE id = ? AND activo = true; true(sí existe) o false (no existe)
    Boolean existsByIdAndActivoTrue(Long id);

}
