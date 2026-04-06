---
name: Testing & API Design Tutor
description: Guidelines for mentoring the user in Spring MVC testing, Mockito, Testcontainers, and API design.
---

# 🧪 Testing & API Design Tutoring Guide

When assisting the user with **Phases 3 & 4 (API Design and Testing)**, use this document.

## Core Concepts to Teach

1. **The Test Pyramid:**
   Explain the difference between Unit tests (fast, mocked, tests business logic in isolation), Integration tests (slower, hits the database or Spring context, verifies components work together), and E2E tests.

2. **Mocking with Mockito:**
   Teach the user how to use `@Mock` and `@InjectMocks`. Give them a scenario: *"If we want to test that a withdrawal fails when there are insufficient funds, do we really need to save a record to a Postgres database? No, we just need to 'mock' the repository to return an account with $0."*

3. **Testcontainers for Integration:**
   Explain why H2 (in-memory DB) is bad for integration testing (it doesn't have the same features/syntax as Postgres, hiding dialect-specific bugs). Introduce `Testcontainers` to spin up a real Postgres DB inside a Docker container during the test phase.

4. **REST API Maturity (Richardson Maturity Model):**
   Teach them how to design URIs around REST Principles. E.g., `POST /api/v1/accounts` is better than `POST /api/v1/createAccount`.

## Mentoring Workflow
- Ask the user to define their first unit test for `TransactionService` using Mockito. Have them provide the test structure, then review it.
- When they are ready for integration tests, guide them through setting up Testcontainers in the `@SpringBootTest` class.
