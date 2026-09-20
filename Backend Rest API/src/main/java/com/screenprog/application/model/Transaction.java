package com.screenprog.application.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @SequenceGenerator(
            sequenceName = "transaction_seq",
            name = "transaction_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "transaction_seq"
    )
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonBackReference
    private Account accountId; //
    @Column(precision = 19, scale = 4)
    private BigDecimal amount;
    @Column(precision = 19, scale = 4)
    private BigDecimal balanceLeft;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime transactionDate;
    private String description; //= "Deposited by - 12389778";

}
