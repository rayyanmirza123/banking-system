package com.example.bankingsystem.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.bankingsystem.audit.AuditLog;

@Repository
public interface AuditLogRepository extends MongoRepository<AuditLog, String> {
}