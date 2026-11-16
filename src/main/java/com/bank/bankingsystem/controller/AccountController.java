
package com.bank.bankingsystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.bankingsystem.dto.AccountResponseDTO;
import com.bank.bankingsystem.entity.Account;
import com.bank.bankingsystem.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public AccountResponseDTO createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @GetMapping
    public List<AccountResponseDTO> getAllAccount() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public Optional<AccountResponseDTO> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @PutMapping("/{id}")
    public Optional<AccountResponseDTO> updateAccount(
            @PathVariable Long id,
            @RequestBody Account updatedAccount) {

        return accountService.updateAccount(id, updatedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {

        boolean deleted = accountService.deleteAccount(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
