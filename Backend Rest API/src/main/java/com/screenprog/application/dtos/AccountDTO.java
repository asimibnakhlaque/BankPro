package com.screenprog.application.dtos;

import com.screenprog.application.model.AccountType;
import com.screenprog.application.model.Status;

import java.math.BigDecimal;

public record AccountDTO (
    Long customerId,
    BigDecimal balance,
    Status status,
    AccountType type,
    Integer pin
){}
