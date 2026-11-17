
package com.bank.bankingsystem.controller;

import com.bank.bankingsystem.dto.*;
import com.bank.bankingsystem.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@Tag(
        name = "Transaction Management",
        description = "APIs for deposits, withdrawals, transfers and transaction history"
)
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    @Operation(summary = "Deposit money",
            description = "Adds money to an existing bank account")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Deposit successful"),
            @ApiResponse(responseCode = "400", description = "Invalid amount"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    public ResponseEntity<TransactionResponseDTO> deposit(
            @Valid @RequestBody DepositRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transactionService.deposit(request));
    }

    @PostMapping("/withdraw")
    @Operation(summary = "Withdraw money",
            description = "Withdraws money if sufficient balance is available")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Withdrawal successful"),
            @ApiResponse(responseCode = "400", description = "Invalid amount or insufficient balance"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    public ResponseEntity<TransactionResponseDTO> withdraw(
            @Valid @RequestBody WithdrawRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transactionService.withdraw(request));
    }

    @PostMapping("/transfer")
    @Operation(summary = "Transfer money",
            description = "Transfers money between two different accounts")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transfer successful"),
            @ApiResponse(responseCode = "400", description = "Invalid transfer or insufficient balance"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    public ResponseEntity<List<TransactionResponseDTO>> transfer(
            @Valid @RequestBody TransferRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transactionService.transfer(request));
    }

    @GetMapping("/account/{accountId}")
    @Operation(summary = "Get transaction history",
            description = "Returns transactions belonging to a specific account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "History retrieved"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionHistory(
            @Parameter(description = "ID of the bank account", example = "1")
            @PathVariable Long accountId) {

        return ResponseEntity.ok(
                transactionService.getTransactionHistory(accountId)
        );
    }
}
