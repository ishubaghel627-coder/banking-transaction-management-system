package com.bank.bankingsystem.dto;

import com.bank.bankingsystem.entity.Account;

public class AccountMapper {

    public static AccountResponseDTO toDTO(Account account) {

        return new AccountResponseDTO(
                account.getId(),
                account.getAccountHolderName(),
                account.getAccountNumber(),
                account.getBalance()
        );
    }
}