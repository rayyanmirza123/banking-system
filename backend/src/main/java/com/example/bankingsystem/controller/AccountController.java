package com.example.bankingsystem.controller;


import com.example.bankingsystem.repository.AuditLogRepository;
import com.example.bankingsystem.audit.AuditLog;
import com.example.bankingsystem.dto.TransferRequest;
import com.example.bankingsystem.model.Account;
import com.example.bankingsystem.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Account Management", description = "APIs for banking operations")
public class AccountController {

    private final AccountService accountService;
    private final AuditLogRepository auditLogRepository;

    @PostMapping
    @Operation(summary = "Create a new account")
    public ResponseEntity<Account> createAccount(
            @RequestParam String ownerName,
            @RequestParam BigDecimal initialDeposit) {
        Account account = accountService.createAccount(ownerName, initialDeposit);
        auditLogRepository.save(
            new AuditLog("ACCOUNT_CREATED", "Account opened for: " + ownerName)
        );
        return ResponseEntity.ok(account);
    }

    @PostMapping("/transfer")
    @Operation(summary = "Transfer money between accounts")
    public ResponseEntity<String> transferMoney(
            @RequestBody TransferRequest request) {
        accountService.transfer(
            request.sourceAccountId(),
            request.targetAccountId(),
            request.amount()
        );
        auditLogRepository.save(
            new AuditLog("TRANSFER", 
                String.format("Transferred %s from %d to %d", 
                    request.amount(), 
                    request.sourceAccountId(), 
                    request.targetAccountId()))
        );
        return ResponseEntity.ok("Transfer successful");
    }

    @GetMapping("/{id}/balance")
    @Operation(summary = "Get account balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getBalance(id));
    }
}