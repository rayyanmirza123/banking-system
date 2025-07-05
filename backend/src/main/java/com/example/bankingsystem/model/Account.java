package com.example.bankingsystem.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
 
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(unique = true, nullable = false)
 private String accountNumber;  // Format: "RAY2024XXXX" (custom generator)

 private String ownerName;

 @Column(precision = 19, scale = 2)
 private BigDecimal balance;

 @Version
 private Long version;  // Optimistic locking
}