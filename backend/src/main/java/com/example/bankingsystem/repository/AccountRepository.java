package com.example.bankingsystem.repository;



import com.example.bankingsystem.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

 // Pessimistic lock for concurrent access
 @Lock(LockModeType.PESSIMISTIC_WRITE)
 @Query("SELECT a FROM Account a WHERE a.id = :id")
 Optional<Account> findByIdWithLock(@Param("id") Long id);

 // Custom query for account existence check
 boolean existsByAccountNumber(String accountNumber);
}