package com.example.bankingsystem.audit;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "audit_logs")
public class AuditLog {
 public AuditLog(String id, String details) {
		this.id = id;
		this.details = details;
	}
@Id
 private String id;
 private String action; // e.g., "TRANSFER", "ACCOUNT_CREATED"
 private String details;
 private String userId; // Optional: JWT user ID
 private LocalDateTime timestamp = LocalDateTime.now();
}