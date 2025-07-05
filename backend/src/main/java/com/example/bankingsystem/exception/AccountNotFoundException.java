package com.example.bankingsystem.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {
 /**
	 * 
	 */
	private static final long serialVersionUID = 8513505653655424398L;

public AccountNotFoundException(Long accountId) {
     super("Account not found with ID: " + accountId);
 }
}