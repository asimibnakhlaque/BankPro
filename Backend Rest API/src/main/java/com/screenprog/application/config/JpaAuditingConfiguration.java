package com.screenprog.application.config;
import com.screenprog.application.service.JsonWebTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getName)
                .or(()->Optional.of("system"));
    }

    @Bean
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(LocalDateTime.now(ZoneId.of("UTC")));
    }

}
