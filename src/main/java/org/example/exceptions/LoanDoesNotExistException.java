package org.example.exceptions;

public class LoanDoesNotExistException extends Exception {
    public LoanDoesNotExistException(String message) {
        super(message);
    }
}
