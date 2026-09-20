package com.screenprog.application.config;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app.jwt")
@Validated
public record JsonProperties(
        @NotBlank String secretKey,
        @Min(6000) int expiryDuration) {}
