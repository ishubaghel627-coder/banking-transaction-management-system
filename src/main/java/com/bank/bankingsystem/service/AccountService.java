
package com.bank.bankingsystem.service;

import java.util.List;
import java.util.Optional;

import com.bank.bankingsystem.dto.AccountMapper;
import com.bank.bankingsystem.dto.AccountResponseDTO;
import com.bank.bankingsystem.entity.Account;
import com.bank.bankingsystem.repository.AccountRepository;

import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponseDTO createAccount(Account account) {
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.toDTO(savedAccount);
    }

    public List<AccountResponseDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(AccountMapper::toDTO)
                .toList();
    }

    public Optional<AccountResponseDTO> getAccountById(Long id) {
        return accountRepository.findById(id)
                .map(AccountMapper::toDTO);
    }

    public Optional<AccountResponseDTO> updateAccount(Long id, Account updatedAccount) {

        Optional<Account> existingAccount = accountRepository.findById(id);

        if (existingAccount.isPresent()) {
            Account account = existingAccount.get();

            account.setAccountHolderName(updatedAccount.getAccountHolderName());
            account.setAccountNumber(updatedAccount.getAccountNumber());
            account.setBalance(updatedAccount.getBalance());

            Account savedAccount = accountRepository.save(account);

            return Optional.of(AccountMapper.toDTO(savedAccount));
        }

        return Optional.empty();
    }

    public boolean deleteAccount(Long id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
