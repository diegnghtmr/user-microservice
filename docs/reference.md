# Food Court Identity API reference

## API endpoints

Base URL: `http://localhost:8090/user-api`. The running service exposes [Swagger UI](http://localhost:8090/user-api/swagger-ui.html) for request and response schemas.

| Method and path | Purpose |
| --- | --- |
| `POST /auth/login` | Authenticate with email and password and issue a JWT |
| `POST /user/client` | Register a customer |
| `POST /user/owner` | Register an owner account |
| `POST /user/employee` | Register an employee for a restaurant; requires the owner role |
| `POST /user/admin` | Register an administrator account |
| `GET /user/{id}` | Read a user by ID |
| `GET /user/{id}/exists` | Check a user ID’s existence |
| `GET /user/email/{email}` | Read a user by email, including employee name lookup by Traceability |
| `GET /user` | List users |
| `GET /user/owners` | List owners |
| `GET /user/employees/restaurant/{restaurantId}` | List employees assigned to a restaurant |

Login returns a signed JWT with ID and roles and a `restaurantId` claim when present. API callers use `Authorization: Bearer <token>` for protected routes. Spring Security permits unauthenticated access to login, customer registration, and the email lookup; do not assume every user lookup requires a token. Catalog’s existing `/users/{id}` Feign route is not the `/user/{id}` route listed above.

## Data store and environment

PostgreSQL stores user accounts, BCrypt password hashes, and roles. The development database is `user_db`. Its [Compose service](../docker-compose.yml) publishes PostgreSQL at host port `5431` to avoid the catalog’s port `5432`.

| Name | Purpose | Default with a host-run API |
| --- | --- | --- |
| `SPRING_DATASOURCE_URL` | User database JDBC URL | `jdbc:postgresql://localhost:5431/user_db` |
| `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD` | PostgreSQL credentials | Local values in Spring config and Compose; supply private overrides for other environments |

`docker compose up -d user-postgres` starts only PostgreSQL. `docker compose up -d` starts this repository’s API and database. The build’s context test uses its H2 configuration, not the development container.

## Ports and adapters

The domain owns account and login rules. Application DTOs, mappers, and handlers bridge the REST contract to domain services. Infrastructure implements the ports with JPA, BCrypt, and JWT handling; no database or token library is needed in the domain.

| Domain output port | Adapter | Responsibility |
| --- | --- | --- |
| `IUserPersistencePort` | `UserJpaAdapter` | Store and query accounts and roles in PostgreSQL |
| `IPasswordEncoderPort` | `BCryptPasswordEncoderAdapter` | Hash and verify passwords |
| `ITokenProviderPort` | `JwtProviderAdapter` | Issue signed bearer tokens |

The UI uses these endpoints for accounts and sign-in. Order looks up customers; Traceability resolves employee names; Catalog has a client intended to look up owners.

## System diagrams

- [System context](https://github.com/diegnghtmr/food-court-ui/blob/main/docs/project-architecture.png) — UI, services, and data stores.
- [SQL entities](https://github.com/diegnghtmr/food-court-ui/blob/main/docs/sql-entities-diagram.png) — historical relational design; current orders are not stored in PostgreSQL.
- [Document entities](https://github.com/diegnghtmr/food-court-ui/blob/main/docs/nosql-entities-diagram.png) — the order and trace document model.
- [Order lifecycle](https://github.com/diegnghtmr/food-court-ui/blob/main/docs/order-flow-diagram.png) — status transitions through pickup or cancellation.
