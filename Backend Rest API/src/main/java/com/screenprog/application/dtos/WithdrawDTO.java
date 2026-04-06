package com.screenprog.application.dtos;

import java.math.BigDecimal;

public record WithdrawDTO(Long accountNumber, BigDecimal balance, String pin) {
}
