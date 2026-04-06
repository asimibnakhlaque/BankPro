package com.screenprog.application.dtos;

import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record TransferDTO(Long accountIdOfSender, Long accountIdOfReceiver, @Min(1) BigDecimal balance, String pin) {
}
