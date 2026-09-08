# FerreViky

E-commerce system for a hardware store, backend built with Spring Boot. Designed as a real online sales platform: product catalog, shopping cart, secure authentication, and a documented, production-optimized API.

---

## Key Features

- JWT Authentication — secure login and stateless session handling.
- Dynamic filtering with JPA Specifications — product search by multiple combinable criteria.
- Shopping cart — full purchase flow management.
- Query optimization — N+1 problem fixes to improve performance on endpoints with nested relationships.
- API documentation — endpoints documented with Swagger/OpenAPI.
- Unit tests — business logic coverage with JUnit and Mockito.

---

## Tech Stack

- Java + Spring Boot
- Spring Security (JWT)
- Spring Data JPA / Hibernate
- PostgreSQL
- Swagger / OpenAPI
- JUnit + Mockito

---

## Architecture

The project follows a typical Spring Boot layered architecture:

```
Controller → Service → Repository → Entity
```

- Controllers expose the REST API and are documented with Swagger.
- Services contain the business logic.
- Repositories use Spring Data JPA with Specifications for dynamic queries.
- Entities are optimized to avoid N+1 issues via fetch joins/`@EntityGraph` as needed.

---

## Installation and Local Setup

### Prerequisites

- Java 17+ (or the version used in the project)
- PostgreSQL running locally or via Docker
- Maven

### 1. Clone the repository

```bash
git clone https://github.com/LNath4n/Spring-FerreViky.git
cd Spring-FerreViky
```

### 2. Configure the database

Create a database in PostgreSQL:

```sql
CREATE DATABASE ferreviky;
```

Set the credentials in `src/main/resources/application.properties` (or `application.yml`):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ferreviky
spring.datasource.username=your_username
spring.datasource.password=your_password

jwt.secret=your_secret_key
```

### 3. Run the project

```bash
./mvnw clean install
./mvnw spring-boot:run
```

The backend will be available at `http://localhost:8080`.

### 4. API Documentation

With the project running, Swagger documentation will be available at:

```
http://localhost:8080/swagger-ui.html
```

> Adjust routes, ports, and property names according to your repo's actual structure.

---

## Project Status

Currently on pause, but I decided to make it public because it's one of the pieces I'm most proud of in Spring Boot: it reflects solid backend architecture, good security practices, and performance optimization.

---

## Author

**Nathan & Jaqueline**
Full Stack Developer — Java/Spring Boot · Angular · Flutter · Python
