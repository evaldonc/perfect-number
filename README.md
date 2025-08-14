# Perfect Number - Clean Architecture (Spring Boot, Java 17)

## Run (Dev)
1. Start Postgres quickly with Docker Compose:
   ```bash
   docker-compose up -d db
   ```
2. Run app:
   ```bash
   ./mvnw spring-boot:run
   ```
   or via your IDE.

## Run (Full Docker)
```bash
docker-compose up --build
```
- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html

## Endpoint
```
POST /api/v1/perfect-numbers/find
{
  "range": [1, 10000]
}
-> { "numbers": [6,28,496,8128], "count": 4 }
```

All calls are audited into table `audit_logs` storing source IP, request path, range, and the *prime numbers* among the result list (may be empty).

## Tests
- Unit tests for the perfect-number service.
- Integration test using Testcontainers with Postgres.
1. Start Postgres quickly with Docker Compose:
   ```bash
   docker-compose up -d db
   ```
2. Run tests:
    ```bash
    mvn -q -DskipTests=false test
    ```

## Clean Architecture Structure (packages)
- `config` (OpenAPI, etc)
- `domain` (entities, service interface)
- `infrastructure` (JPA, mappers, implementations, services)
- `web` (controllers, DTOs)
