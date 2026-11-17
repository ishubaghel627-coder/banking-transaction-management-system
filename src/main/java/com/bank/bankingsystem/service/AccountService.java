
package com.bank.bankingsystem.service;

import java.util.List;
import java.util.Optional;

import com.bank.bankingsystem.dto.AccountMapper;
import com.bank.bankingsystem.dto.AccountResponseDTO;
import com.bank.bankingsystem.entity.Account;
import com.bank.bankingsystem.repository.AccountRepository;

import org.springframework.stereotype.Service;
import com.bank.bankingsystem.exception.ResourceNotFoundException;

import com.bank.bankingsystem.exception.AccountAlreadyExistsException;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponseDTO createAccount(Account account) {

        if (accountRepository.existsByAccountNumber(account.getAccountNumber())) {
            throw new AccountAlreadyExistsException("Account number already exists");
        }

        Account savedAccount = accountRepository.save(account);

        return AccountMapper.toDTO(savedAccount);
    }

    public List<AccountResponseDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(AccountMapper::toDTO)
                .toList();
    }

    public AccountResponseDTO getAccountById(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account not found with ID: " + id
                ));

        return AccountMapper.toDTO(account);
    }


    public AccountResponseDTO updateAccount(Long id, Account updatedAccount) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account not found with ID: " + id
                ));

        account.setAccountHolderName(updatedAccount.getAccountHolderName());
        account.setAccountNumber(updatedAccount.getAccountNumber());
        account.setBalance(updatedAccount.getBalance());

        Account savedAccount = accountRepository.save(account);

        return AccountMapper.toDTO(savedAccount);
    }


    public void deleteAccount(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account not found with ID: " + id
                ));

        accountRepository.delete(account);
    }
}
