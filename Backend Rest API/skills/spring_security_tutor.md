---
name: Spring Security Tutor
description: Mentoring guidelines and technical resources for teaching Spring Security and JWTs.
---

# 🛡️ Spring Security Tutoring Guide

When assisting the user with **Phase 1: Security Hardening**, rely on these principles and steps:

## Core Concepts to Teach
1. **The Filter Chain Architecture:** Ensure the user understands that Spring Security intercepts HTTP requests via a chain of `Filter`s *before* they reach the `DispatcherServlet` or Controllers.
2. **Stateless Authentication:** Differentiate between session-based cookies and token-based (JWT) authentication. Explain *why* the server doesn't "remember" the user in a stateless API and needs the JWT validated on every request.
3. **Cryptographic Signatures:** Explain that JWTs are signed, not encrypted. If the secret key changes (like on a server restart with dynamic keys), all currently active tokens become un-verifiable. This is why a static, secure environment variable `JWT_SECRET` is necessary.
4. **Method Security:** Explain how AOP (Aspect-Oriented Programming) allows `@PreAuthorize` to proxy a service method. Show how Spring checks the `SecurityContextHolder` before proceeding into the service.

## Mentoring Workflow
- **Do not write the `SecurityFilterChain` Bean for them.**
- Instead, prompt them: *"We need to transition our security config from deprecated WebSecurityConfigurerAdapter-style to component-based SecurityFilterChain (Spring 6). How would you define a `@Bean` that takes an `HttpSecurity` object and permits `/api/public/**` while securing everything else?"*
- If they show you the code for fixing the JWT secret, make sure they test it by generating a token, restarting the application, and proving the token is still valid.
