package com.example.bankingsystem.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankingsystem.audit.AuditLog;
import com.example.bankingsystem.exception.AccountNotFoundException;
import com.example.bankingsystem.exception.InsufficientFundsException;
import com.example.bankingsystem.model.Account;
import com.example.bankingsystem.repository.AccountRepository;
import com.example.bankingsystem.repository.AuditLogRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional  // Critical for atomic transfers!
public class TransferService {

 @Autowired
 private AccountRepository accountRepo;

 @Autowired
 private AuditLogRepository auditRepo; // MongoDB

 public void transfer(Long sourceId, Long targetId, BigDecimal amount) {
     Account source = accountRepo.findById(sourceId)
             .orElseThrow(() -> new AccountNotFoundException(sourceId));
     
     Account target = accountRepo.findById(targetId)
             .orElseThrow(() -> new AccountNotFoundException(targetId));

     if (source.getBalance().compareTo(amount) < 0) {
         auditRepo.save(new AuditLog("TRANSFER_FAILED", "Insufficient funds"));
         throw new InsufficientFundsException("TRANSFER_FAILED : Insufficient funds");
     }

     source.setBalance(source.getBalance().subtract(amount));
     target.setBalance(target.getBalance().add(amount));

     accountRepo.saveAll(List.of(source, target));
     auditRepo.save(new AuditLog("TRANSFER", 
         String.format("Transferred %s from %d to %d", amount, sourceId, targetId)));
 }
}