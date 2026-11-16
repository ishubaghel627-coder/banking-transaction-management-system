package com.bank.bankingsystem.dto;

import java.math.BigDecimal;

public class AccountResponseDTO {

    private Long id;
    private String accountHolderName;
    private String accountNumber;
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