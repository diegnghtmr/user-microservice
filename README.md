# User Microservice

User management and authentication service for the Food Court system. It provides JWT-based login, user profiles, and role data consumed by the other services.

## System Context
- Part of the Food Court microservices suite.
- Source of truth for users and roles (admin, owner, employee, client).
- Used by Food Court, Order, and Traceability services for validation and lookup.

## Responsibilities
- User registration and authentication.
- Role-aware profiles and restaurant ownership or assignment data.
- User data lookup for downstream services.

## Architecture (Hexagonal / Ports and Adapters)
This service follows Hexagonal Architecture with explicit ports and adapters.
- `domain/`: core models, business rules, ports, and use cases.
- `application/`: DTOs, mappers, and handlers that orchestrate use cases.
- `infrastructure/`: REST controllers, JPA adapters, security configuration, and JWT implementation.

## Tech Stack
- Spring Boot 4, Spring Security, JWT
- Spring Data JPA + PostgreSQL
- MapStruct, Springdoc OpenAPI

## Data Store
- PostgreSQL (local default: `jdbc:postgresql://localhost:5431/user_db`)

## API
- Base URL: `http://localhost:8090/user-api`
- Swagger UI: `http://localhost:8090/user-api/swagger-ui.html`

## Local Development
1. Start the database: `docker compose up -d` (uses `docker-compose.yml`)
2. Run the service: `./gradlew bootRun`
3. Run tests: `./gradlew test`

## System Diagrams
![Project Architecture](https://raw.githubusercontent.com/diegnghtmr/food-court-ui/main/docs/project-architecture.png)
![SQL Entities Diagram](https://raw.githubusercontent.com/diegnghtmr/food-court-ui/main/docs/sql-entities-diagram.png)
![Order Flow Diagram](https://raw.githubusercontent.com/diegnghtmr/food-court-ui/main/docs/order-flow-diagram.png)
![NoSQL Entities Diagram](https://raw.githubusercontent.com/diegnghtmr/food-court-ui/main/docs/nosql-entities-diagram.png)

## Related Repositories
- [user-microservice](https://github.com/diegnghtmr/user-microservice)
- [food-court-microservice](https://github.com/diegnghtmr/food-court-microservice)
- [order-microservice](https://github.com/diegnghtmr/order-microservice)
- [traceability-microservice](https://github.com/diegnghtmr/traceability-microservice)
- [food-court-ui](https://github.com/diegnghtmr/food-court-ui)
