# FerreViky

Sistema de e-commerce para una ferretería, backend construido con Spring Boot. Pensado como plataforma real de ventas online: catálogo de productos, carrito de compras, autenticación segura y una API documentada y optimizada para producción.

---

## Características principales

- Autenticación con JWT — login seguro y manejo de sesiones sin estado.
- Filtrado dinámico con JPA Specifications — búsqueda de productos por múltiples criterios combinables.
- Carrito de compras — gestión completa del flujo de compra.
- Optimización de consultas — solución de problemas N+1 para mejorar el rendimiento en endpoints con relaciones anidadas.
- Documentación de API — endpoints documentados con Swagger/OpenAPI.
- Tests unitarios — cobertura de lógica de negocio con JUnit y Mockito.

---

## Stack tecnológico

- Java + Spring Boot
- Spring Security (JWT)
- Spring Data JPA / Hibernate
- PostgreSQL
- Swagger / OpenAPI
- JUnit + Mockito

---

## Arquitectura

El proyecto sigue una arquitectura por capas típica de Spring Boot:

```
Controller → Service → Repository → Entity
```

- Los Controllers exponen la API REST y están documentados con Swagger.
- Los Services contienen la lógica de negocio.
- Los Repositories usan Spring Data JPA con Specifications para consultas dinámicas.
- Las entidades están optimizadas para evitar problemas de N+1 mediante fetch joins/`@EntityGraph` según el caso.

---

## Instalación y ejecución local

### Requisitos previos

- Java 17+ (o la versión que uses en el proyecto)
- PostgreSQL corriendo localmente o vía Docker
- Maven

### 1. Clonar el repositorio

```bash
git clone https://github.com/LNath4n/Spring-FerreViky.git
cd Spring-FerreViky
```

### 2. Configurar la base de datos

Crea una base de datos en PostgreSQL:

```sql
CREATE DATABASE ferreviky;
```

Configura las credenciales en `src/main/resources/application.properties` (o `application.yml`):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ferreviky
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password

jwt.secret=tu_clave_secreta
```

### 3. Levantar el proyecto

```bash
./mvnw clean install
./mvnw spring-boot:run
```

El backend quedará disponible en `http://localhost:8080`.

### 4. Documentación de la API

Con el proyecto corriendo, la documentación Swagger estará disponible en:

```
http://localhost:8080/swagger-ui.html
```

> Ajusta rutas, puertos y nombres de propiedades según la estructura real de tu repo.

---

## Estado del proyecto

Proyecto actualmente en pausa, pero decidí hacerlo público porque es una de las piezas de las que más orgulloso estoy en Spring Boot: refleja buen manejo de arquitectura backend, buenas prácticas de seguridad y optimización de rendimiento.

---

## Autor

**Nathan**
Full Stack Developer — Java/Spring Boot · Angular · Flutter · Python
