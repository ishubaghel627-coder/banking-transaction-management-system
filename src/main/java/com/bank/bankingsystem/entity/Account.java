package com.bank.bankingsystem.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "accounts")

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Account holder name is required")
    @Column(nullable=false)
    @Schema(
            description = "Full name of the bank account holder",
            example = "Rahul Sharma"
    )
    private String accountHolderName;

    @NotBlank(message = "Account number is required")
    @Column(unique = true,nullable=false)
    @Schema(
            description = "Unique bank account number",
            example = "1001002001"
    )
    private String accountNumber;

    @NotNull(message = "Balance cannot be null")
    @DecimalMin(value = "0.00", message = "Balance cannot be negative")
    @Column (nullable=false,precision=19,scale=2)
    @Schema(
            description = "Initial account balance",
            example = "5000.00"
    )
    private BigDecimal balance;

    public Account(){
    }
    public Account(String accountHolderName,
                   String accountNumber,
                   BigDecimal balance){
        this.accountHolderName=accountHolderName;
        this.accountNumber=accountNumber;
        this.balance=balance;
    }
    public Long getId(){
        return id;
    }
    public String getAccountHolderName(){
        return accountHolderName;
    }
    public String getAccountNumber(){
        return accountNumber;
    }
    public BigDecimal getBalance(){
        return balance;
    }


    public void setAccountHolderName(String accountHolderName){
        this.accountHolderName=accountHolderName;
    }
    public void setAccountNumber(String accountNumber){
        this.accountNumber=accountNumber;
    }
    public void setBalance(BigDecimal balance) {
        this.balance=balance;
    }
}
