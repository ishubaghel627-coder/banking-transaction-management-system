
package com.bank.bankingsystem.controller;

import java.util.List;
import java.util.Optional;
import com.bank.bankingsystem.entity.Account;
import com.bank.bankingsystem.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }
    @GetMapping
    public List<Account> getAllAccount(){
        return accountService.getAllAccounts();
    }
    @GetMapping("/{id}")
    public Optional<Account> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }
    @PutMapping("/{id}")
    public Optional<Account> updateAccount(
            @PathVariable Long id,
            @RequestBody Account updatedAccount) {

        return accountService.updateAccount(id, updatedAccount);
    }
}
