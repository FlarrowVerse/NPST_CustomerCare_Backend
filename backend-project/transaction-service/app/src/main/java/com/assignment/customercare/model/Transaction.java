package com.assignment.customercare.model;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("transactions")
public class Transaction {
    
    @Id
    private long id;

    private String transactionId;
    private BigDecimal amount;
    private String status; // SUCCESS, FAILED, PENDING

    private String customerId;
    private String merchant;
    private String paymentMethod;

    private Instant createdAt;

    private Boolean refunded;
    private String parentTransactionId;
}
