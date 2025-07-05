package com.example.bankingsystem.service;



import com.example.bankingsystem.exception.InsufficientFundsException;
import com.example.bankingsystem.model.Account;
import com.example.bankingsystem.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {

 private final AccountRepository accountRepository;

 // Atomic account creation
 public Account createAccount(String ownerName, BigDecimal initialDeposit) {
     Account account = Account.builder()
             .accountNumber(generateAccountNumber())
             .ownerName(ownerName)
             .balance(initialDeposit)
             .build();
     return accountRepository.save(account);
 }

 // Secure money transfer
 @Transactional
 public void transfer(Long sourceId, Long targetId, BigDecimal amount) {
     Account source = accountRepository.findByIdWithLock(sourceId)
             .orElseThrow(() -> new RuntimeException("Source account not found"));
     
     Account target = accountRepository.findByIdWithLock(targetId)
             .orElseThrow(() -> new RuntimeException("Target account not found"));

     if (source.getBalance().compareTo(amount) < 0) {
         throw new InsufficientFundsException("Insufficient balance");
     }

     source.setBalance(source.getBalance().subtract(amount));
     target.setBalance(target.getBalance().add(amount));

     // No explicit save needed - @Transactional handles it
 }
 
 @Transactional
 public BigDecimal getBalance(Long id) {
	 Account source = accountRepository.findByIdWithLock(id).orElseThrow(() -> new RuntimeException("Unable to fetch balance"));
	 return source.getBalance();
 }

 // Custom account number generator
 private String generateAccountNumber() {
     return "RAY" + System.currentTimeMillis() % 10000;
 }
}