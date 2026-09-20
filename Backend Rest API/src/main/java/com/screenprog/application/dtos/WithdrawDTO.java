package com.screenprog.application.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record WithdrawDTO(@NotNull Long accountNumber, @Min(1) @Digits(integer = 12, fraction = 2) BigDecimal balance, @Pattern(regexp = "^\\d{4}$") @NotNull String pin) {
}
