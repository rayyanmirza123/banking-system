package com.example.bankingsystem.exception;


import com.example.bankingsystem.audit.AuditLog;
import com.example.bankingsystem.repository.AuditLogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

 private final AuditLogRepository auditLogRepository;

 public GlobalExceptionHandler(AuditLogRepository auditLogRepository) {
     this.auditLogRepository = auditLogRepository;
 }

 @ExceptionHandler(Exception.class)
 public ResponseEntity<String> handleException(Exception ex) {
     auditLogRepository.save(
         new AuditLog("ERROR", ex.getMessage())
     );
     return ResponseEntity.internalServerError().body(ex.getMessage());
 }
}