# MELI-ecommerce — README

*Version:* 1.2.0

**Project brief:** MELI-ecommerce is a Spring Boot web application that models a simplified order management system for an e-commerce scenario. It demonstrates a robust service design with entities for `Client`, `Item`, and `Order`, RESTful CRUD endpoints (global and nested), validation, H2-based development DB seeding, profiles for multiple environments, and a global exception handling strategy. The project was shaped after a real incident analysis where misconfiguration and DB node failures caused production outages — the project focuses on resilience, clear API documentation (Swagger), and safe environment separation.

**Short description:** This repository contains a Spring Boot (Java) implementation of a small e-commerce backend (clients, items, orders) built with best practices: DTOs for requests/responses, constructor injection via Lombok, JPA entities, validation (`jakarta.validation`), H2 for dev/test, and a global exception handler. It includes nested client-scoped order endpoints for secure, contextual access and global order endpoints for administrative use.

---

### CHANGELOG
This project maintains a changelog to track releases, fixes, and noteworthy decisions. See the `CHANGELOG.md` file in the repo root for the full history. Below is a short summary of the initial entries.

*v1.0.0 — Initial release*

- Implemented core domain: `Client`, `Item`, `Order` entities.
- Implemented full CRUD for Items, Clients, and Orders (global and client-scoped endpoints).
- . . .

## Table of contents

* [Prerequisites](#prerequisites)
* [Project structure & description](#project-structure--description)
* [Quick start (recommended: use the provided startup scripts)](#quick-start-development)
* [How to run (Linux/maOS and Windows options)](#how-to-run-platform-specific)
* [Profiles & configuration files](#profiles--configuration-files)
* [H2 + DB seed (dummy data)](#h2--db-seed-dummy-data)
* [API Documentation (swagger)](#api-documentation-swagger--openapi)
* [API endpoints (summary + examples)](#api-endpoints-summary--examples)
* [Validation & error handling](#validation--error-handling)
* [Important code locations](#important-code-locations)
* [Troubleshooting](#troubleshooting)
* [Extras & future improvements](#extras--future-improvements)

---

## Prerequisites

* Java 17+
* Maven (use `./mvnw` wrapper included)
* Lombok enabled in your IDE (annotation processing on)
* (Optional) Docker for running a production DB locally if desired

---

## Project structure & description

High-level responsibilities:

* `Client`, `Item`, `Order` JPA entities (Order references one Client and one Item).
* DTOs separate input (create/update) from output (response). Response DTOs include summary objects for related entities (e.g., item name and price) so clients get useful information without exposing entities.
* Controllers expose both global and nested routes (e.g., `/api/v1/orders` and `/api/v1/clients/{clientId}/orders`).
* Services implement business logic and ownership checks (ensuring a client actually owns the requested order).
* Global exception advice formats validation and business errors consistently.
* Profiles allow different configuration per environment (dev/test/prod).
* H2 in-memory DB + `data.sql` for easy local testing.

---

## Quick start (development)
We include cross-platform startup scripts to simplify running the app with the correct profile. Use these scripts instead of running Maven commands manually.

### General pattern:

- Default profile is dev (H2 in-memory, H2 console enabled).
- You can run the script with an explicit profile name to start the application under dev, test, or prod.
- For dev and test the script runs the app via the Maven wrapper (fast feedback, H2 console).
- For prod the script runs the packaged JAR (and will build it if missing).
- Before you run: make the Unix script executable once (if using Linux/macOS). In Windows run the batch file.

### How to run (platform-specific)

#### Linux / macOS

- Use the provided `start.sh` script.
- Running the script with no arguments starts the app in the default development profile.
- You may run the script with a profile name (for example `dev`, `test`, or `prod`) to choose an explicit profile.

#### Examples of usage:

- Start with default (development): run the `start.sh` script with no args.
- Start with test profile: run the `start.sh` script and pass `test` as the profile.
- Start with prod profile: run the `start.sh` script and pass `prod` — the script will run the packaged JAR (building it first if necessary).

> Notes:  
> The dev and test profiles use H2 (in-memory).  
>The prod profile expects a persistent DB configuration (see Profiles section).

#### Windows (CMD)

- Use the included `start.bat` to start the application.
- The batch script accepts an optional profile argument (default is `dev`).
- Running `start.bat` with prod will attempt to locate or build the JAR and start it with the prod profile.

#### Example
```bash 
    # Linux or macOS
    start.sh
    start.sh test
    start.sh prod

    # Windows
    
    start.bat 
    start.bat test
    start.bat prod
```

## Profiles & configuration files

Files in `src/main/resources/`:

* `application.yml` — shared settings + default active profile (`dev`).
* `application-dev.yml` — H2 in-memory DB, `ddl-auto=update`/`create`, SQL init settings.
* `application-test.yml` — H2 with `create-drop` and test-friendly settings.
* `application-prod.yml` — example production config (Postgres/MySQL placeholders; uses env vars for secrets).

Activate a profile:

* `SPRING_PROFILES_ACTIVE=dev` (env var), or
* `java -jar target/meliecommerce.jar --spring.profiles.active=prod`, or
* `./mvnw spring-boot:run -Dspring-boot.run.profiles=test` or
* Use the platform specific start script.

**Note:** For `data.sql` to run after the schema is created, ensure dev/test profiles use `spring.jpa.hibernate.ddl-auto=create` or `update` **and** `spring.sql.init.mode=always`.

---

## H2 + DB seed (dummy data)

Place `data.sql` in `src/main/resources/`. Example seed inserts 5 rows per table (clients, items, orders). Ensure schema is created first (see Profiles section). Example inserts use table names matching your entities (`CLIENTS`, `ITEMS`, `ORDERS`) and date strings `YYYY-MM-DD`.

---

## API Documentation (Swagger / OpenAPI)

This project includes **Springdoc OpenAPI** integration for automatically generated and interactive API documentation.

### Overview

* Swagger UI is available once the app is running.
* Each REST controller, DTO, and field includes annotations (`@Operation`, `@Schema`, etc.) to enrich the documentation.
* The docs describe available endpoints, request/response formats, parameter validation rules, and example payloads.

### Accessing the Documentation

After starting the application (any profile):

* **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* **OpenAPI JSON:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
* **OpenAPI YAML:** [http://localhost:8080/v3/api-docs.yaml](http://localhost:8080/v3/api-docs.yaml)

### Configuration Summary

The Swagger/OpenAPI setup is initialized automatically by **Springdoc** through the `springdoc-openapi-starter-webmvc-ui` dependency.
An optional configuration class (e.g., `SwaggerConfig`) defines metadata such as API title, version, description, and contact information.

### Notes

* The documentation dynamically reflects validation annotations and response schemas.
* You don’t need a separate `swagger.yaml` — it’s generated at runtime.

## API endpoints (summary + examples)

### Items

* `GET /api/v1/items?page=0&size=10` — paginated items
  Example: `GET http://localhost:8080/api/v1/items?page=0&size=10`
* `GET /api/v1/items/{id}`
* `POST /api/v1/items`
* `PUT /api/v1/items/{id}`
* `DELETE /api/v1/items/{id}`

### Clients

* `GET /api/v1/clients?page=0&size=10`
* `GET /api/v1/clients/{id}`
* `POST /api/v1/clients`
* `PUT /api/v1/clients/{id}`
* `DELETE /api/v1/clients/{id}`

### Orders (global)

* `GET /api/v1/orders?page=0&size=20`
* `GET /api/v1/orders/{id}`
* `POST /api/v1/orders` — body: `{"clientId":..., "itemId":..., "purchaseDate":"YYYY-MM-DD","deliveryDate":"YYYY-MM-DD"}`
* `PUT /api/v1/orders/{id}`
* `DELETE /api/v1/orders/{id}`

### Client-scoped Orders (nested)

* `GET /api/v1/clients/{clientId}/orders`
* `GET /api/v1/clients/{clientId}/orders/{orderId}` — returns 404 if order doesn't belong to client
* `POST /api/v1/clients/{clientId}/orders` — URL carries clientId; body contains itemId and dates
* `PUT /api/v1/clients/{clientId}/orders/{orderId}`
* `DELETE /api/v1/clients/{clientId}/orders/{orderId}`

**Example create (global)**:

```bash
curl -X POST http://localhost:8080/api/v1/orders \
 -H "Content-Type: application/json" \
 -d '{"clientId":1,"itemId":1,"purchaseDate":"2025-10-17","deliveryDate":"2025-10-20"}'
```

**Example create (scoped)**:

```bash
curl -X POST http://localhost:8080/api/v1/clients/1/orders \
 -H "Content-Type: application/json" \
 -d '{"itemId":1,"purchaseDate":"2025-10-17","deliveryDate":"2025-10-20"}'
```

---

## Validation & error handling

* DTOs use `jakarta.validation` (`@NotNull`, `@Min`, `@NotBlank`, etc.).
* Controllers use `@Valid` on request bodies and `@Validated` at class level to enable `@PathVariable` validation.
* Global exception handlers in `GlobalExceptionControllerAdvice` handle:

    * `MethodArgumentNotValidException` (invalid request body) → 400 with per-field messages
    * `ConstraintViolationException` (invalid path vars / request params) → 400 with property-path messages
    * `ResponseException` / `NotFoundException` (business errors) → mapped status codes (e.g., 404)
    * generic `Exception` → 500 fallback

**Error shape** (default):

```json
{ "status": 400, "message": "field1: message; field2: message" }
```

---

## Important code locations

* Entities: `com.pikolinc.meliecommerce.domain.entity` (`Client`, `Item`, `Order`)
* DTOs: `com.pikolinc.meliecommerce.domain.dto.*` (`OrderCreateDTO`, `OrderResponseDTO`, `ClientSummaryDTO`, `ItemResponseDTO`)
* Repositories: `com.pikolinc.meliecommerce.repository` (`ClientRepository`, `ItemRepository`, `OrderRepository`)
* Services: `com.pikolinc.meliecommerce.service` (`ItemService`, `ClientService`, `OrderService`, `ClientOrderService`)
* Controllers: `com.pikolinc.meliecommerce.controller` (`ItemController`, `ClientController`, `OrderController`, `ClientOrderController`)
* Exception handling: `com.pikolinc.meliecommerce.exception.GlobalExceptionControllerAdvice`

---

## Key design & team decisions (with justifications)

1. **Use DTOs for request/response**

    * *Why:* Prevents leaking JPA internals, provides stable API contracts, and allows shaping responses (e.g., include item summary instead of raw `itemId`).

2. **Expose both global and client-scoped endpoints**

    * *Why:* Global endpoints support admin/reporting; scoped endpoints are more natural and secure for client-facing UIs (explicit ownership).

3. **Constructor injection (Lombok `@RequiredArgsConstructor`)**

    * *Why:* Cleaner, testable code and avoids null DI issues—use `private final` fields.

4. **Validation split**: `MethodArgumentNotValidException` for bodies, `ConstraintViolationException` for path vars/params

    * *Why:* Provides precise errors and consistent 400 responses.
5. **H2 for dev/test, external DB for prod**

    * *Why:* Fast dev iteration and clean tests with in-memory DB; production should use persistent DB and `ddl-auto=validate`.

## Extras & future improvements
* Add **MapStruct** to eliminate boilerplate mapping between entities and DTOs (compile-time safe).
* Add **Springdoc OpenAPI / Swagger** for interactive API docs (e.g., `/swagger-ui.html`).
* Add **integration tests** with `@SpringBootTest` and `test` profile.
* Introduce **DTO versioning** if API evolves (v1 → v2).
* Add **security** (Spring Security) and per-client authorization (only allow clients to access their data).

