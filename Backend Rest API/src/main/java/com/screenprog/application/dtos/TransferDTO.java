package com.screenprog.application.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record TransferDTO(@NotNull Long accountIdOfSender, @NotNull Long accountIdOfReceiver, @Min(1) @Digits(integer = 12,fraction = 2) BigDecimal balance, @Pattern(regexp = "^\\d{4}$") @NotNull String pin) {
    public TransferDTO{
        if(accountIdOfReceiver != null && accountIdOfReceiver.equals(accountIdOfSender)){
            throw new IllegalArgumentException("Can not transfer funds to the same account");
        }
    }
}
