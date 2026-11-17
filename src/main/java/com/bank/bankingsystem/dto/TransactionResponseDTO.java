
package com.bank.bankingsystem.dto;

import com.bank.bankingsystem.entity.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponseDTO {

    private Long id;
    private TransactionType transactionType;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String description;
    private Long accountId;

    public TransactionResponseDTO(
            Long id,
            TransactionType transactionType,
            BigDecimal amount,
            LocalDateTime transactionDate,
            String description,
            Long accountId) {

        this.id = id;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.description = description;
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public Long getAccountId() {
        return accountId;
    }
}
