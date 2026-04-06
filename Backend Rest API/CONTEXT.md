# Project Context & Learning Path

## Project Context
The base project is a **Bank Account Management System** currently suffering from critical architectural and security flaws. It holds a rating of **3/10 (CRITICAL RISK - NOT PRODUCTION READY)**. The system is built on Spring Boot 3.3.3 and Java, but lacks proper JWT management, financial data precision, transaction isolation, testing, and production-ready DevOps configurations.

## The Improvement Plan & Learning Roadmap

This roadmap acts as both the architectural modernization plan and the syllabus for your learning journey. For each phase, you will write the code while the Agent acts as a tutor guiding you through the "Why", "How", and "What".

### 🛡️ Phase 1: Security Hardening (Current Focus)
**Goal:** Prevent authentication bypasses and account takeovers.
*   **Concepts:** `Spring Security Filter Chain`, `Stateless Authentication vs Sessions`, `Cryptography for JWT`, `Role vs Resource-based Authorization`, `Aspect-Oriented Programming (AOP)`.
*   **Tasks:**
    *   Fix the dynamic/re-generating JWT secret bug. Move the secret to environment properties.
    *   Eliminate hardcoded Admin credentials.
    *   Implement method-level security using `@PreAuthorize` (e.g., verifying `SecurityContext` against the transaction's target `accountId`).
*   **Learning Outline:** Understanding how Spring Security intercepts requests before controllers, the difference between authentication and authorization in Spring, and creating custom security expressions.

### 💰 Phase 2: Data Integrity & Resilient Transactions
**Goal:** Stop precision loss and protect against concurrent race conditions during transactions.
*   **Concepts:** `IEEE 754 Floating-Point Arithmetic limitations`, `The ACID properties`, `Database Isolation Levels`, `Pessimistic vs Optimistic Locking`.
*   **Tasks:**
    *   Refactor the codebase to migrate financial amounts from `Double` to `BigDecimal`.
    *   Implement proper `@Transactional` boundaries on the service layer with appropriate isolation (`Isolation.SERIALIZABLE` or `READ_COMMITTED` with locking).
    *   Apply `@Lock(LockModeType.PESSIMISTIC_WRITE)` / `@Version` where necessary.
    *   Integrate Flyway or Liquibase for versioned database schemas.
*   **Learning Outline:** The dangers of using `Double` for money, understanding when to lock rows at the database level to prevent "lost updates" in concurrent bank transfers, and mastering Spring Data JPA lifecycle.

### 📊 Phase 3: Robust API Design & Clean Architecture
**Goal:** Modernize the API surface and abstract domain models correctly.
*   **Concepts:** `RESTful Maturity Model`, `DTO Pattern`, `Global Exception Handling interceptors`, `Java Bean Validation (JSR-380)`.
*   **Tasks:**
    *   Establish API versioning (e.g., `/api/v1/...`).
    *   Create a `@RestControllerAdvice` wrapper to catch and format exceptions universally (like `InsufficientFundsException`).
    *   Add `@Valid` and custom validation annotations.
    *   Implement OpenAPI/Swagger for self-documenting APIs.
*   **Learning Outline:** Creating unified and consistent API response contracts, separating database entities from web layer requests/responses (DTOs), and intercepting exceptions globally to avoid dirty `try-catch` blocks everywhere.

### 🧪 Phase 4: Testing Mastery
**Goal:** Enforce quality with an 80%+ coverage rate using modern testing paradigms.
*   **Concepts:** `Test Pyramid`, `Mocking vs Stubbing`, `Containerized Integration Tests`.
*   **Tasks:**
    *   Setup `Testcontainers` for PostgreSQL to avoid running integration tests on H2 or fragile local DBs.
    *   Write isolated unit tests for business logic using `Mockito`.
    *   Implement API integration testing using `MockMvc`.
    *   Add Security integration tests using `@WithMockUser`.
*   **Learning Outline:** The value of throwing out H2 for real containerized Postgres instances during the build phase, simulating network/DB failures via Mocks, and testing security contexts without deploying.

### 🔧 Phase 5: Observability & DevOps
**Goal:** Prepare the application to safely run and be monitored in a production environment.
*   **Concepts:** `Metrics, Tracing, Logging`, `Cloud-Native Buildpacks / Multi-stage Docker`, `Application Health`.
*   **Tasks:**
    *   Integrate Spring Boot Actuator for health and metrics.
    *   Setup Prometheus endpoints and Micrometer distributed tracing.
    *   Implement an optimized, multi-stage Dockerfile using Alpine JRE.
*   **Learning Outline:** How to expose internal JMX/Spring metrics over HTTP securely, tracking user journeys across distributed services, and building minimal-footprint, secure container images.
