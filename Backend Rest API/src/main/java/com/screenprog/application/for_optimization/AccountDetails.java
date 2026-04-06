package com.screenprog.application.for_optimization;

import com.screenprog.application.model.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountDetails(Long accountNumber, BigDecimal balance, Status status, LocalDateTime openDate ) {
}
