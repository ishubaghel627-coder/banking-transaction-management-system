
package com.bank.bankingsystem.service;

import com.bank.bankingsystem.dto.*;
import com.bank.bankingsystem.entity.*;
import com.bank.bankingsystem.repository.AccountRepository;
import com.bank.bankingsystem.repository.TransactionRepository;
import com.bank.bankingsystem.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository) {

        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public TransactionResponseDTO deposit(DepositRequestDTO request) {

        Account account = accountRepository
                .findByIdForUpdate(request.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account not found with ID: " + request.getAccountId()
                ));

        account.setBalance(account.getBalance().add(request.getAmount()));

        Transaction transaction = new Transaction(
                TransactionType.DEPOSIT,
                request.getAmount(),
                LocalDateTime.now(),
                "Money deposited",
                account
        );

        accountRepository.save(account);

        Transaction savedTransaction =
                transactionRepository.save(transaction);

        return TransactionMapper.toDTO(savedTransaction);
    }

    @Transactional
    public TransactionResponseDTO withdraw(WithdrawRequestDTO request) {

        Account account = accountRepository
                .findByIdForUpdate(request.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account not found with ID: " + request.getAccountId()
                ));

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient account balance");
        }

        account.setBalance(account.getBalance().subtract(request.getAmount()));

        Transaction transaction = new Transaction(
                TransactionType.WITHDRAWAL,
                request.getAmount(),
                LocalDateTime.now(),
                "Money withdrawn",
                account
        );

        accountRepository.save(account);

        Transaction savedTransaction =
                transactionRepository.save(transaction);

        return TransactionMapper.toDTO(savedTransaction);
    }

    @Transactional
    public List<TransactionResponseDTO> transfer(TransferRequestDTO request) {

        Long fromId = request.getFromAccountId();
        Long toId = request.getToAccountId();

        if (fromId.equals(toId)) {
            throw new IllegalArgumentException(
                    "Source and destination accounts cannot be the same"
            );
        }

        Account fromAccount;
        Account toAccount;

        // Lock accounts in ascending ID order to reduce deadlock risk.
        if (fromId < toId) {
            fromAccount = findLockedAccount(fromId);
            toAccount = findLockedAccount(toId);
        } else {
            toAccount = findLockedAccount(toId);
            fromAccount = findLockedAccount(fromId);
        }

        BigDecimal amount = request.getAmount();

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient account balance");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        LocalDateTime now = LocalDateTime.now();

        Transaction sent = new Transaction(
                TransactionType.TRANSFER_SENT,
                amount,
                now,
                "Transfer to account " + toId,
                fromAccount
        );

        Transaction received = new Transaction(
                TransactionType.TRANSFER_RECEIVED,
                amount,
                now,
                "Transfer from account " + fromId,
                toAccount
        );

        accountRepository.saveAll(List.of(fromAccount, toAccount));

        List<Transaction> savedTransactions =
                transactionRepository.saveAll(List.of(sent, received));

        return savedTransactions.stream()
                .map(TransactionMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TransactionResponseDTO> getTransactionHistory(Long accountId) {

        if (!accountRepository.existsById(accountId)) {
            throw new ResourceNotFoundException(
                    "Account not found with ID: " + accountId
            );
        }

        return transactionRepository
                .findByAccount_IdOrderByTransactionDateDesc(accountId)
                .stream()
                .map(TransactionMapper::toDTO)
                .toList();
    }

    private Account findLockedAccount(Long id) {
        return accountRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account not found with ID: " + id
                ));
    }
}
