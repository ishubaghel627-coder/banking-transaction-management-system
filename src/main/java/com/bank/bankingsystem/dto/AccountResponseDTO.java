package com.bank.bankingsystem.dto;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;

public class AccountResponseDTO {

    @Schema(description = "Unique database ID of the account", example = "1")
    private Long id;

    @Schema(description = "Name of the account holder", example = "Rahul Sharma")
    private String accountHolderName;

    @Schema(description = "Unique bank account number", example = "1001002001")
    private String accountNumber;

    @Schema(description = "Current available account balance", example = "5000.00")
    private BigDecimal balance;

    public AccountResponseDTO(Long id,
                              String accountHolderName,
                              String accountNumber,
                              BigDecimal balance) {
        this.id = id;
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}