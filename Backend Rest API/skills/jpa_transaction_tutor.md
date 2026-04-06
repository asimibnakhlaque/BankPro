---
name: JPA and Transaction Tutor
description: Guidelines for teaching database locking, ACID, and BigDecimal usage in Spring Data JPA.
---

# 💰 JPA & Transaction Tutoring Guide

When assisting the user with **Phase 2: Data Integrity & Transactions**, use this document to guide your instruction.

## Core Concepts to Teach

1. **Floating Point Arithmetic Flaws:** 
   Explain why `Double` and `Float` approximate values. Ask the user to mentally evaluate or run a small java snippet of `0.1 + 0.2`. Show them that precision loss in a Banking app leads to actual money loss. Teach `BigDecimal` and the importance of constructing it from `String` (e.g., `new BigDecimal("0.1")`), not Double.

2. **ACID Properties & The "@Transactional" Boundary:**
   Explain what happens when a database transaction fails halfway. Teach how Spring's `@Transactional` annotation manages opening, committing, and rolling back database transactions automatically, mostly via AOP.

3. **Concurrency and Lost Updates:**
   Describe a scenario: *User A and User B both try to withdraw $100 from an account that only has $150 simultaneously.* Both read $150. Both do math: 150 - 100 = 50. Both save $50. The bank just lost $50.
   Teach the user about **Pessimistic Locking** (`@Lock(LockModeType.PESSIMISTIC_WRITE)`) where the database locks the row physically, vs **Optimistic Locking** (`@Version`) where a version column ensures no one else modified the row since we read it.

## Mentoring Workflow
- **Do not bulk-replace Double with BigDecimal for them.**
- Suggest they find all entity classes containing financial data. Let them write the migration. Explain that they also need to update the database schema.
- Challenge them to write an integration test that spans 10 threads doing simultaneous withdrawals to prove their lock works.
