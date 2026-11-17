
package com.bank.bankingsystem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.bank.bankingsystem.dto.AccountResponseDTO;
import com.bank.bankingsystem.entity.Account;
import com.bank.bankingsystem.service.AccountService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/accounts")
@Tag(
        name = "Account Management",
        description = "APIs for creating, retrieving, updating and deleting bank accounts"
)
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @Operation(
            summary = "Create a new bank account",
            description = "Creates a bank account using the account holder name, account number and initial balance"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Bank account created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid account information"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Account number already exists"
            )
    })
    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(
            @Valid @RequestBody Account account) {

        AccountResponseDTO response = accountService.createAccount(account);

        return ResponseEntity.status(201).body(response);
    }
    @Operation(
            summary = "Get all bank accounts",
            description = "Retrieves a list of all registered bank accounts"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Accounts retrieved successfully"
            )
    })
    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> getAllAccount() {

        return ResponseEntity.ok(accountService.getAllAccounts());
    }
    @Operation(
            summary = "Get account by ID",
            description = "Retrieves account details using the account ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Account retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> getAccountById(
            @Parameter(
                    description = "Unique ID of the bank account",
                    example = "1"
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(accountService.getAccountById(id));
    }
    @Operation(
            summary = "Update bank account",
            description = "Updates the details of an existing bank account"
    )

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Account updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid account information"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Account number already exists"
            )
    })

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> updateAccount(
            @Parameter(
                    description = "Unique ID of the bank account to update",
                    example = "1"
            )
            @PathVariable Long id,
            @Valid @RequestBody Account updatedAccount) {

        return ResponseEntity.ok(
                accountService.updateAccount(id, updatedAccount)
        );
    }
    @Operation(
            summary = "Delete bank account",
            description = "Deletes an existing bank account using its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Account deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account not found"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(
            @Parameter(
                    description = "Unique ID of the bank account to delete",
                    example = "1"
            )
            @PathVariable Long id) {

        accountService.deleteAccount(id);

        return ResponseEntity.noContent().build();
    }
}
