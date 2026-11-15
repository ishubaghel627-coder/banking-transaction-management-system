package com.bank.bankingsystem.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String accountHolderName;

    @Column(unique = true,nullable=false)
    private String accountNumber;

    @Column (nullable=false,precision=19,scale=2)
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
