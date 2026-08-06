


# 📦 Documento Técnico Completo: API REST Supermercado

> **🚀 Despliegue en la Nube:**
> Este proyecto está 100% configurado y listo para desplegarse en plataformas PaaS como Railway. Incluye variables de entorno preparadas para soportar despliegue automático desde GitHub y conexión a bases de datos MySQL gestionadas en la nube, así como soporte para proxy inverso (HTTPS).

## 1. 📄 Enunciado de la Prueba Técnica

**Objetivo**
Evaluar conocimientos en Java + Spring Boot, incluyendo el desarrollo de una API RESTful completa que implemente operaciones CRUD con JPA, relaciones entre entidades, control de errores y excepciones, uso de DTOs, buenas prácticas REST y programación funcional (uso de lambdas y streams) donde aplique.

**Descripción del caso**
Una reconocida cadena de supermercados desea digitalizar su sistema de control de ventas. Para ello necesita una API que permita (de forma básica):
* Registrar productos con sus respectivos precios.
* Gestionar las sucursales donde se venden los productos.
* Registrar ventas realizadas en una sucursal, especificando los productos vendidos y cantidades.
* Consultar ventas por sucursal, totalizar ingresos, filtrar productos más vendidos, etc.

**Entidades principales**
* **Sucursal:** Ubicación física del supermercado.
* **Producto:** Artículo que puede venderse.
* **Venta:** Contiene una o más líneas de productos, asociadas a una sucursal.

**Relaciones:**
* Una Sucursal puede tener muchas ventas.
* Una Venta tiene muchos productos asociados.
* Un mismo Producto puede estar en muchas ventas.

**Requisitos técnicos**
* Spring Boot con JPA para manejo de bases de datos (MySQL).
* Exponer endpoints RESTful (GET, POST, PUT, DELETE).
* Utilizar DTOs para separar modelo de dominio y representación externa.
* Manejo de errores con `ResponseEntity`, códigos HTTP correctos y mensajes claros.
* **Uso de lambdas o streams en al menos una operación del backend.**
* Organización modular del proyecto (service, repository, controller).

**Endpoints Principales:**
* `GET /api/productos` - Listar productos.
* `GET /api/productos/{id}` - Buscar producto por ID.
* `POST /api/productos` - Crear producto (nombre, precio, categoría).
* `PUT /api/productos/{id}` - Actualizar producto.
* `DELETE /api/productos/{id}` - Eliminar producto (Borrado lógico).
* `POST /api/ventas` - Registrar venta (Payload con `sucursalId` y un arreglo de `detalle` con `productoId` y `cantidad`).
* `GET /api/ventas/{id}` - Buscar venta por ID.
* `GET /api/ventas?sucursalId=1&fecha=2025-06-01` - Ventas por sucursal y fecha.
* `DELETE /api/ventas/{id}` - Anular venta (Borrado lógico).
* `GET /api/estadisticas/producto-mas-vendido` - Calcular usando Java Streams.
* `GET /api/estadisticas/top-productos?quantity=5` - Ranking de productos más vendidos.

---

## 2. 🏗️ Arquitectura del Sistema

El proyecto implementa una **Arquitectura N-Tier (Por Capas)** basada en los principios SOLID, específicamente el Principio de Responsabilidad Única (SRP) y el Principio de Inversión de Dependencias (DIP).

El flujo de la información es unidireccional y estricto:
`Cliente (Postman/Swagger) -> Controller -> Service (Interface) -> Service (Impl) -> Repository -> Base de Datos (MySQL)`

Cada capa solo se comunica con su capa adyacente y utiliza objetos de transferencia (DTOs) o mapeadores (Mappers) para no exponer la lógica interna ni la estructura de la base de datos.

---

## 3. 📁 Estructura del Proyecto

El código está organizado de manera altamente modularizada bajo el paquete raíz `com.supermercado`:

```text
src/main/java/com/supermercado/
├── SupermercadoApplication.java          # Clase principal (Punto de arranque de Spring)
├── controller/                           # CAPA DE PRESENTACIÓN
│   ├── ProductoController.java
│   ├── SucursalController.java
│   ├── VentaController.java
│   └── EstadisticaController.java
├── dto/                                  # CAPA DE TRANSPORTE DE DATOS
│   ├── request/                          # DTOs de entrada (con validaciones)
│   │   ├── ProductoRequest.java
│   │   ├── SucursalRequest.java
│   │   ├── VentaRequest.java
│   │   └── DetalleVentaRequest.java
│   └── response/                         # DTOs de salida (datos enriquecidos)
│       ├── ProductoResponse.java
│       ├── SucursalResponse.java
│       ├── VentaResponse.java
│       ├── DetalleVentaResponse.java
│       └── ProductoMasVendidoResponse.java
├── entity/                               # CAPA DE PERSISTENCIA (Modelo Relacional)
│   ├── Producto.java
│   ├── Sucursal.java
│   ├── Venta.java
│   └── DetalleVenta.java
├── enums/                                # Catálogos de datos fijos
│   └── CategoriaProducto.java
├── exception/                            # CAPA DE GESTIÓN DE ERRORES
│   ├── ResourceNotFoundException.java
│   ├── BusinessRuleException.java
│   └── GlobalExceptionHandler.java
├── mapper/                               # CAPA DE MAPEO OBJETUAL-RELACIONAL
│   ├── ProductoMapper.java
│   ├── SucursalMapper.java
│   └── VentaMapper.java
├── repository/                           # CAPA DE ACCESO A DATOS
│   ├── ProductoRepository.java
│   ├── SucursalRepository.java
│   ├── VentaRepository.java
│   └── DetalleVentaRepository.java
└── service/                              # CAPA DE LÓGICA DE NEGOCIO
    ├── ProductoService.java              # Interfaces (Contratos)
    ├── SucursalService.java
    ├── VentaService.java
    ├── EstadisticaService.java
    └── impl/                             # Implementaciones reales (La lógica pesada)
        ├── ProductoServiceImpl.java
        ├── SucursalServiceImpl.java
        ├── VentaServiceImpl.java
        └── EstadisticaServiceImpl.java
```

---

## 4. 🔍 Análisis Profundo por Capas

### 4.1. Capa de Presentación (`controller`)
Son los "Meseros" de la aplicación. Su única responsabilidad es recibir las peticiones HTTP, extraer parámetros y delegar al Servicio.
* **Buenas prácticas aplicadas:** Uso estricto de códigos de estado HTTP semánticos (`201 CREATED` para recursos creados, `204 NO CONTENT` para borrados, `200 OK` para lecturas/actualizaciones).
* **Documentación Viva (OpenAPI):** Se utilizan anotaciones de Swagger como `@Tag`, `@Operation` y `@ApiResponses` en cada endpoint para generar una interfaz gráfica interactiva y autoexplicativa.
* **Configuración CORS:** Se implementa `@CrossOrigin(origins = "*")` para permitir la integración con aplicaciones frontend (React, Vue, Angular) desde cualquier dominio.
* **Validación:** Se activa el interceptor de validaciones usando `@Valid` junto a `@RequestBody`, permitiendo que los errores de formato recaigan en el `GlobalExceptionHandler` antes de tocar la lógica de negocio.
* **Parámetros:** Uso correcto de `@PathVariable` para identificadores de recursos en la URL y `@RequestParam` para filtros de búsqueda (como sucursal y fecha).

### 4.2. Capa de Transporte de Datos (`dto`)
Dividida en Request (lo que entra) y Response (lo que sale). Evita exponer las Entities de JPA directamente.
* **Request:** Contienen anotaciones de la especificación Jakarta Validation (`@NotBlank`, `@NotNull`, `@Positive`, `@Min`, `@Size`). Esto garantiza la integridad de los datos en la "frontera" del sistema.
* **Response:** Realizan "Enriquecimiento de Datos". Por ejemplo, `VentaResponse` no solo devuelve el `sucursalId`, sino que incluye el `nombreSucursal` y una lista anidada de `DetalleVentaResponse` con los nombres de los productos, ahorrándole peticiones extra al frontend.

### 4.3. Capa de Persistencia (`entity` y `enums`)
Representación exacta de las tablas en MySQL usando Hibernate/JPA.
* **Uso de `BigDecimal`:** Se descartó el tipo primitivo `Double` por sus errores de precisión en coma flotante. Se mapea a `DECIMAL(10,2)` en MySQL usando `@Column(precision = 10, scale = 2)`.
* **Relaciones JPA:**
    * `@ManyToOne` (En `DetalleVenta` hacia `Venta` y `Producto`): Es el lado dueño de la relación, donde se especifica el `@JoinColumn`.
    * `@OneToMany` (En `Venta` hacia `DetalleVenta` y en `Sucursal` hacia `Venta`): Se usa `mappedBy` para indicarle a Hibernate que no cree columnas redundantes, y se configura `cascade = CascadeType.ALL` y `orphanRemoval = true` para que, al guardar una Venta padre, sus detalles hijos se guarden automáticamente en cascada.
* **Enums:** Se utiliza `@Enumerated(EnumType.STRING)` para guardar el texto ("ALIMENTOS") en la BD en lugar del índice numérico (0, 1), previniendo errores críticos si se modificara el enum en el futuro.
* **Lombok `@Builder.Default`:** Utilizado para inicializar campos como `Boolean activo = true` y listas `new ArrayList<>()`, evitando `NullPointerExceptions` al usar el patrón Builder.

### 4.4. Capa de Acceso a Datos (`repository`)
Interfaces que extienden `JpaRepository`. Aprovechan al máximo las capacidades de Spring Data JPA:
* **Query Derivation (Derivación de consultas):** Spring "lee" el nombre del método y genera el SQL automáticamente (ej: `findByActivoTrueOrderByNombreAsc` genera un `SELECT * WHERE activo=true ORDER BY nombre ASC`).
* **Queries Personalizadas (`@Query`):** Utilizadas cuando la derivación se vuelve ilegible (como en `VentaRepository` para filtrar por fecha y sucursal) o cuando se necesita navegar el grafo de objetos implícitamente en JPQL (como en `DetalleVentaRepository` haciendo `JOIN dv.venta v WHERE v.activo = true`).
* **Patrón de Borrado Lógico:** Ningún repositorio expone métodos que traigan entidades eliminadas. Todos los_findById_ personalizados incluyen el filtro `AndActivoTrue`.

### 4.5. Capa de Mapeo (`mapper`)
Clases de utilidad marcadas con `@Component` que utilizan el patrón Builder.
* **Responsabilidad Única:** Separar la tarea de "copiar datos de un lado a otro" de la lógica del Servicio. Si un campo cambia, solo se modifica aquí.
* **Navegación del Grafo:** En `VentaMapper`, se utiliza programación funcional (`.stream().map(...)`) para transformar una lista de Entities hijas (`List<DetalleVenta>`) en una lista de DTOs hijos (`List<DetalleVentaResponse>`) en una sola línea.

### 4.6. Capa de Lógica de Negocio (`service`)
El núcleo del sistema. Se divide en Interfaces (contratos) e Implementaciones (lógica real).
* **Desacoplamiento:** Los Controllers inyectan la Interfaz (`ProductoService`), no la implementación. Esto permite cambiar la lógica subyacente sin romper el controlador.
* **Gestión de Transacciones (`@Transactional`):**
    * Métodos de escritura (POST, PUT, DELETE) usan `@Transactional` para asegurar atomicidad. Si algo falla al registrar una venta de múltiples productos, se hace un Rollback completo.
    * Métodos de lectura usan `@Transactional(readOnly = true)`, una optimización clave que le indica a Hibernate que no revise si los objetos cambian (Dirty Checking), acelerando las consultas drásticamente.
* **Manejo de `Optional`:** Se utiliza el patrón `.orElseThrow(() -> new ResourceNotFoundException(...))` para abrir de forma segura la caja que devuelve JPA, lanzando excepciones controladas si un ID no existe.

### 4.7. Capa de Gestión de Errores (`exception`)
* **Excepciones Personalizadas:** Se crearon dos etiquetas de error, `ResourceNotFoundException` (para búsquedas fallidas -> 404) y `BusinessRuleException` (para reglas rotas -> 400), en lugar de lanzar excepciones genéricas de Java.
* **`@RestControllerAdvice`:** Intercepta cualquier excepción lanzada en cualquier Controller de forma global. Construye respuestas JSON estandarizadas que incluyen un *timestamp*, el *status code* HTTP, un *error* legible y el *mensaje* específico, ocultando las trazas feas del servidor al cliente.

---

## 5. 🧠 Decisiones Técnicas Destacadas

### 5.1. Programación Funcional (Streams & Lambdas)
Se cumplió el requisito del enunciado en tres niveles de complejidad:
1. **Nivel Básico (Mapeo):** Transformar listas de Entities a Responses en los Services y Mappers (`lista.stream().map(mapper::toResponse).collect(Collectors.toList())`).
2. **Nivel Intermedio (Reducción):** En `VentaServiceImpl`, para calcular el total de una venta sin usar un bucle `for`:
   ```java
   request.getDetalle().stream()
       .map(detalleReq -> crearDetalleVenta(venta, detalleReq)) // Crea el objeto
       .map(DetalleVenta::getSubtotal)                       // Extrae solo el dinero
       .reduce(BigDecimal.ZERO, BigDecimal::add);              // Los suma a todos
   ```
3. **Nivel Avanzado (Agrupación y Estadísticas):** En `EstadisticaServiceImpl`, se utilizó `Collectors.groupingBy()` para agrupar una lista plana de detalles en un Mapa donde la clave es el Producto y el valor es la suma de sus cantidades, usando luego `Comparator.comparingLong()` para encontrar el máximo.

### 5.2. Borrado Lógico (Soft Delete)
En sistemas financieros o de ventas, borrar registros físicamente (`DELETE FROM`) destruye el historial y la integridad referencial. En su lugar, se implementa un campo `activo` (Boolean). Al "eliminar", se cambia a `false`. Todas las consultas de listado y búsqueda están configuradas explícitamente para ignorar los registros donde `activo = false`.

### 5.3. Seguridad por Diseño en las Interfaces
El enunciado indicaba: *"Las ventas NO SE PUEDEN MODIFICAR sin permisos de superusuario"*. En lugar de crear el método y poner un `if` de permisos adentro, la interfaz `VentaService` **simplemente omite** la declaración del método `actualizar()`. Esto blinda la aplicación: es estructuralmente imposible que un Controller modifique una venta porque el contrato no lo permite.

---

## 6. 🚀 Guía Rápida de Ejecución y Pruebas

### Requisitos
* Java 21+
* MySQL Server corriendo localmente en puerto 3306 (o usar variables de entorno en la nube).
* Cliente REST (Postman o Swagger UI recomendado).

### Configuración de BD (Variables de Entorno)
El archivo `application.properties` está configurado para funcionar en local y en la nube (Railway) mediante variables de entorno, manteniendo seguras las credenciales:
```properties
server.port=${PORT:8080}
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:supermercado}?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true
spring.datasource.username=${MYSQL_USER:root}
spring.datasource.password=${MYSQL_PASSWORD:abc123.}
spring.jpa.hibernate.ddl-auto=update

# Configuración de Proxy para Railway (soporte HTTPS)
server.forward-headers-strategy=framework
```

### Flujo de Prueba en Postman / Swagger
1. **Crear Sucursal:** `POST /api/sucursales` `{"nombre": "Central", "direccion": "Av. 1"}`
2. **Crear Productos:** `POST /api/productos` `{"nombre": "Arroz", "precio": 10.50, "categoria": "ALIMENTOS"}`
3. **Registrar Venta:**
   `POST /api/ventas`
   ```json
   {
       "sucursalId": 1,
       "detalle": [
           { "productoId": 1, "cantidad": 2 }
       ]
   }
   ```
4. **Consultar Estadísticas (Streams en acción):**
    * `GET /api/estadisticas/producto-mas-vendido`
    * `GET /api/estadisticas/top-productos?quantity=5`

---

## 7. 🛠️ Stack Tecnológico

* **Lenguaje:** Java 21
* **Framework:** Spring Boot 4
* **Módulos Spring:** Spring Web, Spring Data JPA, Spring Validation
* **ORM:** Hibernate 7.4.1
* **Motor de Base de Datos:** MySQL 8
* **Despliegue en la Nube:** Railway (PaaS con integración de GitHub y MySQL gestionado)
* **Documentación de API:** Swagger / SpringDoc OpenAPI
* **Librerías de Soporte:** Lombok (Reducción de código boilerplate)

---
*Documentación generada como parte de la entrega de la Prueba Técnica de Spring Boot.*