package com.example.bankingsystem.exception;

public class InsufficientFundsException extends RuntimeException {
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public InsufficientFundsException(String message) {
     super(message);
 }
}