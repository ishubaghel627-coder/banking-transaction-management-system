package com.bank.bankingsystem.service;
import java.util.List;
import java.util.Optional;

import com.bank.bankingsystem.entity.Account;
import com.bank.bankingsystem.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    public Account createAccount(Account account){
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }
    public Optional<Account> getAccountById(Long id){
        return accountRepository.findById(id);
    }
    public Optional<Account> updateAccount(Long id, Account updatedAccount) {

        Optional<Account> existingAccount = accountRepository.findById(id);

        if (existingAccount.isPresent()) {

            Account account = existingAccount.get();

            account.setAccountHolderName(updatedAccount.getAccountHolderName());

            account.setAccountNumber(updatedAccount.getAccountNumber());

            account.setBalance(updatedAccount.getBalance());

            return Optional.of(accountRepository.save(account));
        }

        return Optional.empty();
    }
}
